package com.via.account.app.service;

import com.via.account.app.storage.MemberImageStorage;
import com.via.account.app.repository.MemberRepository;
import com.via.account.domain.enums.MemberAuthority;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.vo.MemberImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Tag("UNIT_TEST")
@ExtendWith(MockitoExtension.class)
@DisplayName("MemberManager 단위 테스트")
class MemberManagerTest {

    @InjectMocks
    private MemberManager memberManager;

    @Mock
    private MemberImageStorage memberImageStorage;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("createAndSave 메서드는")
    class Describe_createAndSave {

        @Nested
        @DisplayName("이미지 URL이 제공되면")
        class Context_with_image_url {

            @Test
            @DisplayName("이미지를 저장하고 회원을 생성한다")
            void it_saves_image_and_creates_member() {
                // given
                String email = "test@example.com";
                String imageUrl = "https://example.com/image.jpg";
                MemberImage memberImage = createMemberImage();
                Member expectedMember = createMember(email, memberImage);

                given(memberImageStorage.save(imageUrl)).willReturn(memberImage);
                given(memberRepository.save(any(Member.class))).willReturn(expectedMember);

                // when
                Member result = memberManager.createAndSave(email, imageUrl);

                // then
                assertThat(result).isNotNull();
                assertThat(result.email()).isEqualTo(email);
                assertThat(result.image()).isEqualTo(memberImage);

                verify(memberImageStorage).save(imageUrl);
                verify(memberRepository).save(any(Member.class));
            }
        }

        @Nested
        @DisplayName("이미지 URL이 null이면")
        class Context_without_image_url {

            @Test
            @DisplayName("빈 이미지로 회원을 생성한다")
            void it_creates_member_with_empty_image() {
                // given
                String email = "test@example.com";
                Member expectedMember = createMember(email, MemberImage.empty());

                given(memberRepository.save(any(Member.class))).willReturn(expectedMember);

                // when
                Member result = memberManager.createAndSave(email, null);

                // then
                assertThat(result).isNotNull();
                assertThat(result.email()).isEqualTo(email);

                verify(memberImageStorage, never()).save(anyString());
                verify(memberRepository).save(any(Member.class));
            }
        }
    }

    @Nested
    @DisplayName("updateNickname 메서드는")
    class Describe_updateNickname {

        @Nested
        @DisplayName("회원이 존재하면")
        class Context_member_exists {

            @Test
            @DisplayName("닉네임을 변경하고 저장한다")
            void it_updates_nickname_and_saves() {
                // given
                MemberId memberId = new MemberId(1L);
                String newNickname = "NewNickname";
                Member existingMember = createMember("test@example.com", MemberImage.empty());
                Member updatedMember = existingMember.updateNickname(newNickname);

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

                // when
                Member result = memberManager.updateNickname(memberId, newNickname);

                // then
                assertThat(result).isNotNull();
                verify(memberRepository).findById(memberId);
                verify(memberRepository).save(any(Member.class));
            }
        }

        @Nested
        @DisplayName("회원이 존재하지 않으면")
        class Context_member_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                MemberId memberId = new MemberId(1L);
                String newNickname = "NewNickname";

                given(memberRepository.findById(memberId)).willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> memberManager.updateNickname(memberId, newNickname))
                        .isInstanceOf(RuntimeException.class);

                verify(memberRepository).findById(memberId);
                verify(memberRepository, never()).save(any(Member.class));
            }
        }
    }

    @Nested
    @DisplayName("updateProfileImage 메서드는")
    class Describe_updateProfileImage {

        @Nested
        @DisplayName("회원이 존재하면")
        class Context_member_exists {

            @Test
            @DisplayName("새 이미지를 저장하고 기존 이미지를 삭제한다")
            void it_saves_new_image_and_deletes_old_image() {
                // given
                MemberId memberId = new MemberId(1L);
                InputStream inputStream = new ByteArrayInputStream("test".getBytes());
                String fileName = "test.jpg";
                String contentType = "image/jpeg";
                long size = 1024L;

                MemberImage oldImage = createMemberImage();
                MemberImage newImage = new MemberImage("/test/path");

                Member existingMember = createMember("test@example.com", oldImage);
                Member updatedMember = existingMember.updateProfileImage(newImage);

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                given(memberImageStorage.save(inputStream, fileName, contentType, size)).willReturn(newImage);
                given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

                // when
                Member result = memberManager.updateProfileImage(memberId, inputStream, fileName, contentType, size);

                // then
                assertThat(result).isNotNull();
                verify(memberRepository).findById(memberId);
                verify(memberImageStorage).save(inputStream, fileName, contentType, size);
                verify(memberImageStorage).delete(oldImage);
                verify(memberRepository).save(any(Member.class));
            }

            @Test
            @DisplayName("이미지 저장과 삭제가 올바른 순서로 실행된다")
            void it_executes_operations_in_correct_order() {
                // given
                MemberId memberId = new MemberId(1L);
                InputStream inputStream = new ByteArrayInputStream("test".getBytes());
                String fileName = "test.jpg";
                String contentType = "image/jpeg";
                long size = 1024L;

                MemberImage oldImage = createMemberImage();
                MemberImage newImage = new MemberImage("/test/path");

                Member existingMember = createMember("test@example.com", oldImage);
                Member updatedMember = existingMember.updateProfileImage(newImage);

                given(memberRepository.findById(memberId)).willReturn(Optional.of(existingMember));
                given(memberImageStorage.save(inputStream, fileName, contentType, size)).willReturn(newImage);
                given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

                // when
                memberManager.updateProfileImage(memberId, inputStream, fileName, contentType, size);

                // then
                var inOrder = inOrder(memberImageStorage, memberRepository);
                inOrder.verify(memberRepository).findById(memberId);
                inOrder.verify(memberImageStorage).save(inputStream, fileName, contentType, size);
                inOrder.verify(memberImageStorage).delete(oldImage);
                inOrder.verify(memberRepository).save(any(Member.class));
            }
        }

        @Nested
        @DisplayName("회원이 존재하지 않으면")
        class Context_member_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                MemberId memberId = new MemberId(1L);
                InputStream inputStream = new ByteArrayInputStream("test".getBytes());
                String fileName = "test.jpg";
                String contentType = "image/jpeg";
                long size = 1024L;

                given(memberRepository.findById(memberId)).willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> 
                    memberManager.updateProfileImage(memberId, inputStream, fileName, contentType, size))
                        .isInstanceOf(RuntimeException.class);

                verify(memberRepository).findById(memberId);
                verify(memberImageStorage, never()).save(any(), any(), any(), anyLong());
                verify(memberImageStorage, never()).delete(any());
                verify(memberRepository, never()).save(any(Member.class));
            }
        }
    }

    // 테스트용 헬퍼 메서드
    private Member createMember(String email, MemberImage image) {
        return Member.builder()
                .memberId(new MemberId(1L))
                .nickname("TestUser")
                .email(email)
                .image(image)
                .authorities(List.of(MemberAuthority.USER))
                .build();
    }

    private MemberImage createMemberImage() {
        return new MemberImage("/test/path");
    }
}
