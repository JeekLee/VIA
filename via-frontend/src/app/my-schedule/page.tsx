
import {GoogleLoginButton, KakaoLoginButton} from "@/features/account/auth";

export default function LoginPage() {
  return (
    <div className="min-h-[calc(100vh-56px)] bg-[#191a23] flex items-center justify-center">
      <div className="w-full max-w-md p-8">
        <p className="text-[#858699] text-center mb-8">
          소셜 계정으로 간편하게 시작하세요
        </p>

        <div className="space-y-4">
          <GoogleLoginButton />
          <KakaoLoginButton />
        </div>

        <p className="text-[#858699] text-xs text-center mt-8">
          로그인 시 <span className="text-[#575bc7]">이용약관</span> 및{" "}
          <span className="text-[#575bc7]">개인정보처리방침</span>에 동의하게 됩니다.
        </p>
      </div>
    </div>
  );
}
