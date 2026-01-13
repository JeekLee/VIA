package com.via.common.app.career.service;

import com.via.common.app.career.dao.CareerInfoDao;
import com.via.common.app.career.dto.CareerInfo;
import com.via.common.app.career.repository.CareerRepository;
import com.via.common.app.career.storage.CareerImageStorage;
import com.via.common.domain.career.enums.Gender;
import com.via.common.domain.career.model.Career;
import com.via.common.domain.career.model.id.CareerOwnerId;
import com.via.common.domain.career.model.vo.Address;
import com.via.common.domain.career.model.vo.CareerImage;
import com.via.common.domain.career.model.vo.Contact;
import com.via.common.domain.career.model.vo.Personal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Tag("UNIT_TEST")
@ExtendWith(MockitoExtension.class)
@DisplayName("CareerUseCase UseCase")
class CareerUseCaseTest {

    @InjectMocks
    private CareerUseCase careerUseCase;

    @Mock
    private CareerImageStorage careerImageStorage;

    @Mock
    private CareerRepository careerRepository;

    @Mock
    private CareerInfoDao careerInfoDao;

    @Nested
    @DisplayName("createAndSave method")
    class Describe_createAndSave {

        private final CareerOwnerId ownerId = new CareerOwnerId(1L);
        private final InputStream inputStream = new ByteArrayInputStream("test".getBytes());
        private final String fileName = "profile.jpg";
        private final String contentType = "image/jpeg";
        private final long size = 1024L;
        private final String name = "TestUser";
        private final LocalDate birthDate = LocalDate.of(1990, 1, 1);
        private final Gender gender = Gender.MALE;
        private final String phone = "010-1234-5678";
        private final String email = "hong@example.com";
        private final String zipCode = "12345";
        private final String roadAddress = "서울특별시 테헤란로 212";
        private final String detailAddress = "7동 1302호";

        @Test
        @DisplayName("it_saves_image_and_creates_career")
        void it_saves_image_and_creates_career() {
            // given
            CareerImage careerImage = createCareerImage();
            given(careerImageStorage.save(inputStream, fileName, contentType, size))
                    .willReturn(careerImage);

            // when
            careerUseCase.createAndSave(
                    ownerId, inputStream, fileName, contentType, size,
                    name, birthDate, gender,
                    phone, email,
                    zipCode, roadAddress, detailAddress
            );

            // then
            verify(careerImageStorage).save(inputStream, fileName, contentType, size);
            
            ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
            verify(careerRepository).save(careerCaptor.capture());
            
            Career savedCareer = careerCaptor.getValue();
            assertThat(savedCareer.careerOwnerId()).isEqualTo(ownerId);
            assertThat(savedCareer.image()).isEqualTo(careerImage);
        }

        @Test
        @DisplayName("Personal 정보가 바르게 설정된다.")
        void it_sets_personal_info_correctly() {
            // given
            CareerImage careerImage = createCareerImage();
            given(careerImageStorage.save(inputStream, fileName, contentType, size))
                    .willReturn(careerImage);

            // when
            careerUseCase.createAndSave(
                    ownerId, inputStream, fileName, contentType, size,
                    name, birthDate, gender,
                    phone, email,
                    zipCode, roadAddress, detailAddress
            );

            // then
            ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
            verify(careerRepository).save(careerCaptor.capture());
            
            Career savedCareer = careerCaptor.getValue();
            Personal personal = savedCareer.personal();
            assertThat(personal.name()).isEqualTo(name);
            assertThat(personal.birthDate()).isEqualTo(birthDate);
            assertThat(personal.gender()).isEqualTo(gender);
        }

        @Test
        @DisplayName("Contact 정보가 바르게 설정된다.")
        void it_sets_contact_info_correctly() {
            // given
            CareerImage careerImage = createCareerImage();
            given(careerImageStorage.save(inputStream, fileName, contentType, size))
                    .willReturn(careerImage);

            // when
            careerUseCase.createAndSave(
                    ownerId, inputStream, fileName, contentType, size,
                    name, birthDate, gender,
                    phone, email,
                    zipCode, roadAddress, detailAddress
            );

            // then
            ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
            verify(careerRepository).save(careerCaptor.capture());
            
            Career savedCareer = careerCaptor.getValue();
            Contact contact = savedCareer.contact();
            assertThat(contact.phone()).isEqualTo(phone);
            assertThat(contact.email()).isEqualTo(email);
        }

        @Test
        @DisplayName("Address 정보가 바르게 설정된다.")
        void it_sets_address_info_correctly() {
            // given
            CareerImage careerImage = createCareerImage();
            given(careerImageStorage.save(inputStream, fileName, contentType, size))
                    .willReturn(careerImage);

            // when
            careerUseCase.createAndSave(
                    ownerId, inputStream, fileName, contentType, size,
                    name, birthDate, gender,
                    phone, email,
                    zipCode, roadAddress, detailAddress
            );

            // then
            ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
            verify(careerRepository).save(careerCaptor.capture());
            
            Career savedCareer = careerCaptor.getValue();
            Address address = savedCareer.address();
            assertThat(address.zipCode()).isEqualTo(zipCode);
            assertThat(address.road()).isEqualTo(roadAddress);
            assertThat(address.detail()).isEqualTo(detailAddress);
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        private final CareerOwnerId ownerId = new CareerOwnerId(1L);
        private final String name = "김철수";
        private final LocalDate birthDate = LocalDate.of(1985, 5, 15);
        private final Gender gender = Gender.MALE;
        private final String phone = "010-9876-5432";
        private final String email = "kim@example.com";
        private final String zipCode = "54321";
        private final String roadAddress = "부산시 무슨무슨 주소";
        private final String detailAddress = "무슨동 무슨무슨호";

        @Nested
        @DisplayName("Context_career_exists")
        class Context_career_exists {

            @Test
            @DisplayName("Career 정보가 업데이트된다.")
            void it_updates_career_info() {
                // given
                Career existingCareer = createCareer();
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));

                // when
                careerUseCase.update(
                        ownerId,
                        name, birthDate, gender,
                        phone, email,
                        zipCode, roadAddress, detailAddress
                );

                // then
                verify(careerRepository).findByMemberId(ownerId);
                verify(careerRepository).save(any(Career.class));
            }

            @Test
            @DisplayName("Personal 정보가 업데이트 된다.")
            void it_updates_personal_info() {
                // given
                Career existingCareer = createCareer();
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));

                // when
                careerUseCase.update(
                        ownerId,
                        name, birthDate, gender,
                        phone, email,
                        zipCode, roadAddress, detailAddress
                );

                // then
                ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
                verify(careerRepository).save(careerCaptor.capture());
                
                Career updatedCareer = careerCaptor.getValue();
                Personal personal = updatedCareer.personal();
                assertThat(personal.name()).isEqualTo(name);
                assertThat(personal.birthDate()).isEqualTo(birthDate);
                assertThat(personal.gender()).isEqualTo(gender);
            }

            @Test
            @DisplayName("Contact 정보가 업데이트 된다.")
            void it_updates_contact_info() {
                // given
                Career existingCareer = createCareer();
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));

                // when
                careerUseCase.update(
                        ownerId,
                        name, birthDate, gender,
                        phone, email,
                        zipCode, roadAddress, detailAddress
                );

                // then
                ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
                verify(careerRepository).save(careerCaptor.capture());
                
                Career updatedCareer = careerCaptor.getValue();
                Contact contact = updatedCareer.contact();
                assertThat(contact.phone()).isEqualTo(phone);
                assertThat(contact.email()).isEqualTo(email);
            }

            @Test
            @DisplayName("Address 정보가 업데이트 된다.")
            void it_updates_address_info() {
                // given
                Career existingCareer = createCareer();
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));

                // when
                careerUseCase.update(
                        ownerId,
                        name, birthDate, gender,
                        phone, email,
                        zipCode, roadAddress, detailAddress
                );

                // then
                ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
                verify(careerRepository).save(careerCaptor.capture());
                
                Career updatedCareer = careerCaptor.getValue();
                Address address = updatedCareer.address();
                assertThat(address.zipCode()).isEqualTo(zipCode);
                assertThat(address.road()).isEqualTo(roadAddress);
                assertThat(address.detail()).isEqualTo(detailAddress);
            }
        }

        @Nested
        @DisplayName("Career가 존재하지 않는다면")
        class Context_career_not_found {

            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                // given
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> careerUseCase.update(
                        ownerId,
                        name, birthDate, gender,
                        phone, email,
                        zipCode, roadAddress, detailAddress
                )).isInstanceOf(RuntimeException.class);

                verify(careerRepository).findByMemberId(ownerId);
                verify(careerRepository, never()).save(any(Career.class));
            }
        }
    }

    @Nested
    @DisplayName("updateImage 메서드는")
    class Describe_updateImage {

        private final CareerOwnerId ownerId = new CareerOwnerId(1L);
        private final InputStream inputStream = new ByteArrayInputStream("new-image".getBytes());
        private final String fileName = "new-profile.jpg";
        private final String contentType = "image/jpeg";
        private final long size = 2048L;

        @Nested
        @DisplayName("Career가 존재하면면")
        class Context_career_exists {

            @Test
            @DisplayName("기존 이미지를 삭제해고 새로운 이미지를 저장한다")
            void it_deletes_old_and_saves_new_image() {
                // given
                Career existingCareer = createCareer();
                CareerImage oldImage = existingCareer.image();
                CareerImage newImage = new CareerImage("/test/path");

                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));
                given(careerImageStorage.save(inputStream, fileName, contentType, size))
                        .willReturn(newImage);

                // when
                careerUseCase.updateImage(ownerId, inputStream, fileName, contentType, size);

                // then
                verify(careerRepository).findByMemberId(ownerId);
                verify(careerImageStorage).delete(oldImage);
                verify(careerImageStorage).save(inputStream, fileName, contentType, size);
                verify(careerRepository).save(any(Career.class));
            }

            @Test
            @DisplayName("it_executes_operations_in_correct_order")
            void it_executes_operations_in_correct_order() {
                // given
                Career existingCareer = createCareer();
                CareerImage oldImage = existingCareer.image();
                CareerImage newImage = new CareerImage("/test/path");

                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));
                given(careerImageStorage.save(inputStream, fileName, contentType, size))
                        .willReturn(newImage);

                // when
                careerUseCase.updateImage(ownerId, inputStream, fileName, contentType, size);

                // then
                var inOrder = inOrder(careerRepository, careerImageStorage);
                inOrder.verify(careerRepository).findByMemberId(ownerId);
                inOrder.verify(careerImageStorage).delete(oldImage);
                inOrder.verify(careerImageStorage).save(inputStream, fileName, contentType, size);
                inOrder.verify(careerRepository).save(any(Career.class));
            }

            @Test
            @DisplayName("it_sets_new_image_to_career")
            void it_sets_new_image_to_career() {
                // given
                Career existingCareer = createCareer();
                CareerImage newImage = new CareerImage("/test/path");
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.of(existingCareer));
                given(careerImageStorage.save(inputStream, fileName, contentType, size))
                        .willReturn(newImage);

                // when
                careerUseCase.updateImage(ownerId, inputStream, fileName, contentType, size);

                // then
                ArgumentCaptor<Career> careerCaptor = ArgumentCaptor.forClass(Career.class);
                verify(careerRepository).save(careerCaptor.capture());
                
                Career updatedCareer = careerCaptor.getValue();
                assertThat(updatedCareer.image()).isEqualTo(newImage);
            }
        }

        @Nested
        @DisplayName("Context_career_not_found")
        class Context_career_not_found {

            @Test
            @DisplayName("it_throws_exception")
            void it_throws_exception() {
                // given
                given(careerRepository.findByMemberId(ownerId))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> 
                        careerUseCase.updateImage(ownerId, inputStream, fileName, contentType, size))
                        .isInstanceOf(RuntimeException.class);

                verify(careerRepository).findByMemberId(ownerId);
                verify(careerImageStorage, never()).delete(any());
                verify(careerImageStorage, never()).save(any(), any(), any(), anyLong());
                verify(careerRepository, never()).save(any(Career.class));
            }
        }
    }

    @Nested
    @DisplayName("Describe_getCareerInfo")
    class Describe_getCareerInfo {

        private final CareerOwnerId ownerId = new CareerOwnerId(1L);

        @Nested
        @DisplayName("Context_career_info_exists")
        class Context_career_info_exists {

            @Test
            @DisplayName("CareerInfo�?반환?�다")
            void it_returns_career_info() {
                // given
                CareerInfo careerInfo = createCareerInfo();
                given(careerInfoDao.findByMemberId(ownerId))
                        .willReturn(Optional.of(careerInfo));

                // when
                CareerInfo result = careerUseCase.getCareerInfo(ownerId);

                // then
                assertThat(result).isNotNull();
                assertThat(result.memberId()).isEqualTo(careerInfo.memberId());
                assertThat(result.name()).isEqualTo(careerInfo.name());
                assertThat(result.email()).isEqualTo(careerInfo.email());
                
                verify(careerInfoDao).findByMemberId(ownerId);
            }
        }

        @Nested
        @DisplayName("Context_career_info_not_found")
        class Context_career_info_not_found {

            @Test
            @DisplayName("it_throws_exception")
            void it_throws_exception() {
                // given
                given(careerInfoDao.findByMemberId(ownerId))
                        .willReturn(Optional.empty());

                // when & then
                assertThatThrownBy(() -> careerUseCase.getCareerInfo(ownerId))
                        .isInstanceOf(RuntimeException.class);

                verify(careerInfoDao).findByMemberId(ownerId);
            }
        }
    }

    // ?�스?�용 ?�퍼 메서??
    private CareerImage createCareerImage() {
        return new CareerImage("/test/path");
    }

    private Career createCareer() {
        CareerOwnerId ownerId = new CareerOwnerId(1L);
        CareerImage image = createCareerImage();
        Personal personal = new Personal("홍길동", LocalDate.of(1990, 1, 1), Gender.MALE);
        Contact contact = new Contact("010-1234-5678", "hong@example.com");
        Address address = new Address("12345", "강남구 테헤란로 1234", "456번지");
        
        return Career.create(ownerId, image, personal, contact, address);
    }

    private CareerInfo createCareerInfo() {
        return new CareerInfo(
                1L,
                "https://example.com/career-image.jpg",
                "홍길동",
                LocalDate.of(1990, 1, 1),
                Gender.MALE,
                "010-1234-5678",
                "hong@example.com",
                "12345",
                "강남구 테헤란로 212",
                "456번지"
        );
    }
}
