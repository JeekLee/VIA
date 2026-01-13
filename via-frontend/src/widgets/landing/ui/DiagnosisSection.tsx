export function DiagnosisSection() {
  return (
    <section className="py-20 px-4 md:px-0 flex items-center justify-center bg-[#191a23]">
      <div className="max-w-[1152px] w-full flex flex-col gap-16 px-6">
        {/* Heading */}
        <div className="flex flex-col items-center gap-2 w-full">
          <h2 className="text-[28px] md:text-[36px] leading-[1.5] text-[#eeeffc] text-center font-medium">
            자가 진단을 통해 학습 수준을 점검해보세요.
          </h2>
          <div className="bg-[#393a4b] h-px w-full max-w-[448px]" />
          <p className="text-[#858699] text-[15px] leading-[22px] text-center">
            서브 멘트
          </p>
        </div>

        {/* Cards */}
        <div className="flex flex-col md:flex-row gap-6 items-stretch justify-center max-w-[800px] mx-auto">
          {/* Card 1 */}
          <div className="flex-1 max-w-[352px] w-full mx-auto backdrop-blur-sm bg-[rgba(41,42,53,0.3)] border border-[rgba(57,58,75,0.5)] rounded-[12px] p-8">
            <div className="flex flex-col gap-6">
              <h3 className="text-[22px] leading-[1.5] text-[#eeeffc] text-center font-medium">
                <span className="block">10+개 직무와</span>
                <span className="block">100+개 필요 역량에 대한</span>
                <span className="block">자가 진단 서비스</span>
              </h3>
              <p className="text-[#858699] text-[13px] leading-[1.5] text-center">
                백엔드, 프론트엔드 등 다양한 직무별로 현재 내 실력을 정확하게 파악하고 부족한 부분을 확인하세요.
              </p>
            </div>
          </div>

          {/* Card 2 */}
          <div className="flex-1 max-w-[352px] w-full mx-auto backdrop-blur-sm bg-[rgba(41,42,53,0.3)] border border-[rgba(57,58,75,0.5)] rounded-[12px] p-8">
            <div className="flex flex-col gap-6">
              <h3 className="text-[22px] leading-[1.5] text-[#eeeffc] text-center font-medium">
                <span className="block">자가 진단 결과 기반</span>
                <span className="block">리포트 그리고,</span>
                <span className="block">학습 로드맵까지</span>
              </h3>
              <p className="text-[#858699] text-[13px] leading-[1.5] text-center">
                진단 결과를 바탕으로 맞춤 강의 추천, 적합한 채용공고, 단계별 학습 로드맵까지 한 번에 제공합니다.
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
