package com.via.support.cache.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("UNIT_TEST")
@DisplayName("CustomSpELParserTest")
class CustomSpELParserTest {

    @Nested
    @DisplayName("BasicParameterReference")
    class BasicParameterReference {

        @Test
        @DisplayName("singleParameter_Long")
        void singleParameter_Long() {
            // given
            String[] paramNames = {"userId"};
            Object[] args = {123L};
            String spel = "#userId";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(123L);
        }

        @Test
        @DisplayName("singleParameter_String")
        void singleParameter_String() {
            // given
            String[] paramNames = {"username"};
            Object[] args = {"test"};
            String spel = "#username";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("test");
        }

        @Test
        @DisplayName("multipleParameters_SelectOne")
        void multipleParameters_SelectOne() {
            // given
            String[] paramNames = {"userId", "userName", "age"};
            Object[] args = {100L, "test", 25};
            String spel = "#userName";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("test");
        }
    }

    @Nested
    @DisplayName("StringOperations")
    class StringOperations {

        @Test
        @DisplayName("stringConcatenation_TwoParams")
        void stringConcatenation_TwoParams() {
            // given
            String[] paramNames = {"userId", "orderId"};
            Object[] args = {100L, 200L};
            String spel = "#userId + ':' + #orderId";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result.toString()).isEqualTo("100:200");
        }

        @Test
        @DisplayName("stringConcatenation_ThreeParams")
        void stringConcatenation_ThreeParams() {
            // given
            String[] paramNames = {"prefix", "id", "suffix"};
            Object[] args = {"USER", 123, "CACHE"};
            String spel = "#prefix + '_' + #id + '_' + #suffix";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result.toString()).isEqualTo("USER_123_CACHE");
        }

        @Test
        @DisplayName("literalStringWithParameter")
        void literalStringWithParameter() {
            // given
            String[] paramNames = {"userId"};
            Object[] args = {456L};
            String spel = "'user:' + #userId";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result.toString()).isEqualTo("user:456");
        }
    }

    @Nested
    @DisplayName("ObjectFieldAccess")
    class ObjectFieldAccess {

        @Test
        @DisplayName("accessObjectField")
        void accessObjectField() {
            // given
            String[] paramNames = {"request"};
            Object[] args = {new OrderRequest(100L, "PENDING")};
            String spel = "#request.orderId";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(100L);
        }

        @Test
        @DisplayName("callObjectMethod")
        void callObjectMethod() {
            // given
            String[] paramNames = {"user"};
            Object[] args = {new User(123L, "test")};
            String spel = "#user.getUserKey()";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("USER_123");
        }

        @Test
        @DisplayName("nestedObjectFieldAccess")
        void nestedObjectFieldAccess() {
            // given
            String[] paramNames = {"order"};
            Address address = new Address("Seoul", "12345");
            Order order = new Order(1L, address);
            Object[] args = {order};
            String spel = "#order.address.city";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("Seoul");
        }
    }

    @Nested
    @DisplayName("ConditionalExpressions")
    class ConditionalExpressions {

        @Test
        @DisplayName("ternaryOperator_TrueCase")
        void ternaryOperator_TrueCase() {
            // given
            String[] paramNames = {"isVip", "userId"};
            Object[] args = {true, 100L};
            String spel = "#isVip ? 'VIP_' + #userId : 'NORMAL_' + #userId";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result.toString()).isEqualTo("VIP_100");
        }

        @Test
        @DisplayName("ternaryOperator_FalseCase")
        void ternaryOperator_FalseCase() {
            // given
            String[] paramNames = {"isVip", "userId"};
            Object[] args = {false, 200L};
            String spel = "#isVip ? 'VIP_' + #userId : 'NORMAL_' + #userId";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result.toString()).isEqualTo("NORMAL_200");
        }

        @Test
        @DisplayName("null 체크")
        void nullCheck() {
            // given
            String[] paramNames = {"category"};
            Object[] args = {null};
            String spel = "#category != null ? #category : 'DEFAULT'";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("DEFAULT");
        }

        @Test
        @DisplayName("numberComparison")
        void numberComparison() {
            // given
            String[] paramNames = {"age"};
            Object[] args = {25};
            String spel = "#age >= 20 ? 'ADULT' : 'MINOR'";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("ADULT");
        }
    }

    @Nested
    @DisplayName("ArithmeticOperations")
    class ArithmeticOperations {

        @Test
        @DisplayName("?�셈")
        void addition() {
            // given
            String[] paramNames = {"a", "b"};
            Object[] args = {10, 20};
            String spel = "#a + #b";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(30);
        }

        @Test
        @DisplayName("multiplication")
        void multiplication() {
            // given
            String[] paramNames = {"price", "quantity"};
            Object[] args = {1000, 5};
            String spel = "#price * #quantity";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(5000);
        }

        @Test
        @DisplayName("complexArithmetic")
        void complexArithmetic() {
            // given
            String[] paramNames = {"x", "y", "z"};
            Object[] args = {10, 5, 2};
            String spel = "(#x + #y) * #z";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(30);
        }
    }

    @Nested
    @DisplayName("CollectionOperations")
    class CollectionOperations {

        @Test
        @DisplayName("listIndexAccess")
        void listIndexAccess() {
            // given
            String[] paramNames = {"items"};
            Object[] args = {Arrays.asList("apple", "banana", "cherry")};
            String spel = "#items[0]";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("apple");
        }

        @Test
        @DisplayName("listSize")
        void listSize() {
            // given
            String[] paramNames = {"items"};
            Object[] args = {Arrays.asList(1, 2, 3, 4, 5)};
            String spel = "#items.size()";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(5);
        }

        @Test
        @DisplayName("listIsEmpty")
        void listIsEmpty() {
            // given
            String[] paramNames = {"items"};
            Object[] args = {Arrays.asList()};
            String spel = "#items.isEmpty()";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("LogicalOperations")
    class LogicalOperations {

        @Test
        @DisplayName("AND ?�산")
        void andOperation() {
            // given
            String[] paramNames = {"isActive", "isVerified"};
            Object[] args = {true, true};
            String spel = "#isActive and #isVerified";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(true);
        }

        @Test
        @DisplayName("orOperation")
        void orOperation() {
            // given
            String[] paramNames = {"isAdmin", "isModerator"};
            Object[] args = {false, true};
            String spel = "#isAdmin or #isModerator";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(true);
        }

        @Test
        @DisplayName("notOperation")
        void notOperation() {
            // given
            String[] paramNames = {"isDeleted"};
            Object[] args = {false};
            String spel = "!#isDeleted";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("ExceptionHandling")
    class ExceptionHandling {

        @Test
        @DisplayName("invalidSpelExpression")
        void invalidSpelExpression() {
            // given
            String[] paramNames = {"userId"};
            Object[] args = {123L};
            String spel = "#userId..invalid";

            // when & then
            assertThatThrownBy(() -> CustomSpELParser.parse(paramNames, args, spel))
                    .isInstanceOf(Exception.class);
        }

        @Test
        @DisplayName("nonExistentParameter")
        void nonExistentParameter() {
            // given
            String[] paramNames = {"userId"};
            Object[] args = {123L};
            String spel = "#nonExistent";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then - 존재?��? ?�는 변?�는 null 반환
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("nullObjectFieldAccess")
        void nullObjectFieldAccess() {
            // given
            String[] paramNames = {"request"};
            Object[] args = {null};
            String spel = "#request.orderId";

            // when & then
            assertThatThrownBy(() -> CustomSpELParser.parse(paramNames, args, spel))
                    .isInstanceOf(Exception.class);
        }
    }

    @Nested
    @DisplayName("EdgeCases")
    class EdgeCases {

        @Test
        @DisplayName("emptyParameters")
        void emptyParameters() {
            // given
            String[] paramNames = {};
            Object[] args = {};
            String spel = "'constant'";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("constant");
        }

        @Test
        @DisplayName("nullParameterValue")
        void nullParameterValue() {
            // given
            String[] paramNames = {"value"};
            Object[] args = {null};
            String spel = "#value != null ? #value : 'DEFAULT'";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("DEFAULT");
        }

        @Test
        @DisplayName("specialCharactersInString")
        void specialCharactersInString() {
            // given
            String[] paramNames = {"key"};
            Object[] args = {"user:123:session"};
            String spel = "#key";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result).isEqualTo("user:123:session");
        }

        @Test
        @DisplayName("veryLongExpression")
        void veryLongExpression() {
            // given
            String[] paramNames = {"a", "b", "c", "d", "e"};
            Object[] args = {"A", "B", "C", "D", "E"};
            String spel = "#a + ':' + #b + ':' + #c + ':' + #d + ':' + #e";

            // when
            Object result = CustomSpELParser.parse(paramNames, args, spel);

            // then
            assertThat(result.toString()).isEqualTo("A:B:C:D:E");
        }
    }

    // Test DTOs
    static class OrderRequest {
        private final Long orderId;
        private final String status;

        OrderRequest(Long orderId, String status) {
            this.orderId = orderId;
            this.status = status;
        }

        public Long getOrderId() {
            return orderId;
        }

        public String getStatus() {
            return status;
        }
    }

    static class User {
        private final Long id;
        private final String name;

        User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUserKey() {
            return "USER_" + id;
        }
    }

    static class Order {
        private final Long id;
        private final Address address;

        Order(Long id, Address address) {
            this.id = id;
            this.address = address;
        }

        public Long getId() {
            return id;
        }

        public Address getAddress() {
            return address;
        }
    }

    static class Address {
        private final String city;
        private final String zipCode;

        Address(String city, String zipCode) {
            this.city = city;
            this.zipCode = zipCode;
        }

        public String getCity() {
            return city;
        }

        public String getZipCode() {
            return zipCode;
        }
    }
}