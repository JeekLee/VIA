export function ScreenshotSection() {
  return (
    <section className="py-20 px-4 md:px-0 flex items-center justify-center bg-[#191a23]">
      <div className="max-w-[1152px] w-full flex flex-col gap-16 px-6">
        {/* Heading */}
        <div className="flex flex-col items-center gap-2 w-full">
          <h2 className="text-[28px] md:text-[36px] leading-[1.5] text-[#eeeffc] text-center font-medium">
            <span className="block">정리하기 귀찮은 학습 일정 그리고</span>
            <span className="block">나중에 찾기 어려운 학습 내용</span>
          </h2>
          <div className="bg-[#393a4b] h-px w-full max-w-[448px]" />
          <p className="text-[#858699] text-[15px] leading-[22px] text-center">
            <span className="block">VIA를 통해 학습 일정을 관리하고, 학습 내용을 업로드해 관리할 수 있어요.</span>
            <span className="block">또한 생성된 일정은 외부 캘린더와 쉽게 연동할 수 있어요!</span>
          </p>
        </div>

        {/* Screenshot Placeholder */}
        <div className="w-full max-w-[896px] mx-auto bg-white rounded-[12px] overflow-hidden">
          <div className="w-full aspect-video flex items-center justify-center">
            <p className="text-gray-400 text-lg">스크린샷 이미지 영역</p>
          </div>
        </div>
      </div>
    </section>
  );
}
