"use client";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/shared/ui/accordion";

const faqItems = [
  {
    id: "item-1",
    question: "첫 구매 할인은 언제까지 진행하나요?",
    answer: "첫 구매 할인은 현재 진행 중이며, 종료 시점은 추후 공지될 예정입니다.",
  },
  {
    id: "item-2",
    question: "일회성 구입 후 영구 사용이 가능한지 궁금합니다.",
    answer: "네, 일회성 구매 후 영구적으로 사용 가능합니다.",
  },
  {
    id: "item-3",
    question: "서비스 환불 정책은 어떻게 되나요?",
    answer: "구매 후 7일 이내 미사용 시 100% 환불이 가능합니다.",
  },
  {
    id: "item-4",
    question: "1개의 계정으로 한 명만 사용이 가능한가요?",
    answer: "네, 1개의 계정은 1명의 사용자만 이용 가능합니다.",
  },
];

export function FAQSection() {
  return (
    <section className="py-20 md:py-[201px] px-4 md:px-0 flex items-center justify-center bg-[#191a23]">
      <div className="max-w-[896px] w-full flex flex-col gap-16 px-6">
        {/* Heading */}
        <h2 className="text-[28px] md:text-[36px] leading-[1.5] text-[#eeeffc] text-center font-medium">
          자주 묻는 질문
        </h2>

        {/* FAQ Accordion */}
        <div className="w-full">
          <Accordion type="single" collapsible className="w-full space-y-4">
            {faqItems.map((item, index) => (
              <AccordionItem
                key={item.id}
                value={item.id}
                className={`backdrop-blur-sm bg-[rgba(41,42,53,0.3)] border border-[rgba(57,58,75,0.5)] rounded-[16px] px-6 py-2 ${
                  index === faqItems.length - 1 ? "border-b-0" : ""
                }`}
              >
                <AccordionTrigger className="hover:no-underline">
                  <div className="flex items-center gap-4">
                    <div className="bg-[#6c79ff] rounded-full w-10 h-10 flex items-center justify-center shrink-0">
                      <span className="text-white font-bold text-[15px]">Q</span>
                    </div>
                    <span className="text-[#eeeffc] text-[15px] text-left">
                      {item.question}
                    </span>
                  </div>
                </AccordionTrigger>
                <AccordionContent className="text-[#858699] text-[15px] pl-14">
                  {item.answer}
                </AccordionContent>
              </AccordionItem>
            ))}
          </Accordion>
        </div>
      </div>
    </section>
  );
}
