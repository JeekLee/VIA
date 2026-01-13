export function PainPointCardsSection() {
  return (
    <section className="py-20 md:py-[176px] px-4 md:px-0 flex items-center justify-center bg-[#191a23]">
      <div className="max-w-[1152px] w-full flex flex-col gap-16 px-6">
        {/* Heading */}
        <div className="flex flex-col items-center gap-2 w-full">
          <h2 className="text-[28px] md:text-[36px] leading-[1.5] text-[#eeeffc] text-center font-medium">
            <span className="block">혹시 이런 문제 때문에</span>
            <span className="block">불필요한 시간을 낭비한 경험, 있으신가요?</span>
          </h2>
          <div className="bg-[#393a4b] h-px w-full max-w-[448px]" />
          <p className="text-[#858699] text-[15px] leading-[22px] text-center">
            너무 다양한 학습 및 채용 플랫폼, 일정 관리의 어려움
          </p>
        </div>

        {/* Cards */}
        <div className="flex flex-col md:flex-row gap-6 items-stretch justify-center">
          {/* Card 1 */}
          <div className="flex-1 max-w-[352px] w-full mx-auto backdrop-blur-sm bg-[rgba(41,42,53,0.3)] border border-[rgba(57,58,75,0.5)] rounded-[12px] p-8">
            <div className="flex flex-col gap-6">
              <h3 className="text-[22px] leading-[1.5] text-[#eeeffc] text-center font-medium">
                <span className="block">학습 로드맵 설계,</span>
                <span className="block">혼자 하기엔 너무 막막해요</span>
              </h3>
              <div className="border-t border-[rgba(57,58,75,0.3)] pt-[17px]">
                <p className="text-[#858699] text-[13px] leading-[1.5] text-center">
                  <span className="block">&quot;직무 관련 지식? AI 활용?</span>
                  <span className="block">어떤 걸 배워야</span>
                  <span className="block">성장할 수 있을까...?&quot;</span>
                </p>
              </div>
            </div>
          </div>

          {/* Card 2 */}
          <div className="flex-1 max-w-[352px] w-full mx-auto backdrop-blur-sm bg-[rgba(41,42,53,0.3)] border border-[rgba(57,58,75,0.5)] rounded-[12px] p-8">
            <div className="flex flex-col gap-6">
              <h3 className="text-[22px] leading-[1.5] text-[#eeeffc] text-center font-medium">
                <span className="block">수많은 강의 중에서</span>
                <span className="block">내 수준에 맞는 강의는 뭘까?</span>
              </h3>
              <div className="border-t border-[rgba(57,58,75,0.3)] pt-[17px]">
                <p className="text-[#858699] text-[13px] leading-[1.5] text-center">
                  <span className="block">&quot;초급, 중급, 사전 지식 필요 등</span>
                  <span className="block">내 실력에 맞는 강의는</span>
                  <span className="block">어느 정도 단계일까....?&quot;</span>
                </p>
              </div>
            </div>
          </div>

          {/* Card 3 */}
          <div className="flex-1 max-w-[352px] w-full mx-auto backdrop-blur-sm bg-[rgba(41,42,53,0.3)] border border-[rgba(57,58,75,0.5)] rounded-[12px] p-8">
            <div className="flex flex-col gap-6">
              <h3 className="text-[22px] leading-[1.5] text-[#eeeffc] text-center font-medium">
                <span className="block">내 기술과 채용 공고,</span>
                <span className="block">어떻게 연결할 수 있을까요?</span>
              </h3>
              <div className="border-t border-[rgba(57,58,75,0.3)] pt-[17px]">
                <p className="text-[#858699] text-[13px] leading-[1.5] text-center">
                  <span className="block">&quot;내가 배운 기술과 지식 수준으로</span>
                  <span className="block">이런 직무에</span>
                  <span className="block">지원을 하는 게 맞을까..?&quot;</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
