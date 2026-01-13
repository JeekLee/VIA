"use client";

import { useState, useEffect } from "react";

export function HeroSection() {
  const [typedText, setTypedText] = useState("");
  const [isTypingComplete, setIsTypingComplete] = useState(false);
  const fullText = "2년차 백엔드 개발자인데, 어떤 기술을 추가로 학습하는 게 좋을까?";

  useEffect(() => {
    if (typedText.length < fullText.length) {
      const timeout = setTimeout(() => {
        setTypedText(fullText.slice(0, typedText.length + 1));
      }, 50);
      return () => clearTimeout(timeout);
    } else {
      setIsTypingComplete(true);
    }
  }, [typedText, fullText]);

  return (
    <section className="pt-[120px] md:pt-[148px] pb-[120px] md:pb-[149px] px-4 md:px-0 flex items-center justify-center bg-[#191a23]">
      <div className="max-w-[896px] w-full flex flex-col items-center gap-10 px-6">
        {/* Heading */}
        <div className="flex flex-col items-center gap-3 w-full">
          <h1 className="text-[28px] md:text-[36px] leading-[1.3] text-[#eeeffc] text-center font-semibold">
            <span className="block">어떤 기술을 배워야 할지,</span>
            <span className="block">어디서부터 시작해야 할지 막막하신가요?</span>
          </h1>
          <div className="bg-[#393a4b] h-px w-full max-w-[448px]" />
          <p className="text-[#858699] text-[15px] leading-[22px] text-center">
            자가 진단부터 로드맵까지, 똑똑하게 커리어를 설계해보세요.
          </p>
        </div>

        {/* Input Form */}
        <div className="w-full max-w-[768px] bg-[rgba(41,42,53,0.5)] backdrop-blur-sm border border-[#393a4b] rounded-[16px] p-6">
          <div className="flex flex-col gap-4">
            <p className="text-[#858699] text-[13px] leading-[19.5px]">
              자유롭게 학습 로드맵을 생성해 보세요.
            </p>
            <div className="flex flex-col md:flex-row gap-3 items-stretch md:items-end">
              <div className="flex-1 bg-[#151621] border border-[#393a4b] rounded-[4px] px-[13px] py-[9px] min-h-[36px] flex items-center">
                <span className="text-[#858699] text-[15px] leading-normal">
                  {typedText}
                  <span className="inline-block w-[2px] h-[18px] bg-[#858699] ml-[2px] translate-y-[4px] animate-pulse" />
                </span>
              </div>
              <button
                className={`px-4 py-2 rounded-[4px] text-[15px] font-medium text-white whitespace-nowrap transition-all ${
                  isTypingComplete
                    ? "bg-[#575bc7] hover:bg-[#6b70d6] opacity-100"
                    : "bg-[#575bc7] opacity-50 cursor-not-allowed"
                }`}
                disabled={!isTypingComplete}
              >
                로드맵 생성하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
