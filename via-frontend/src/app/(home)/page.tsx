import {
  HeroSection,
  PainPointCardsSection,
  ScreenshotSection,
  DiagnosisSection,
  FAQSection,
} from "@/widgets/landing";

export default function HomePage() {
  return (
    <div className="min-h-screen bg-[#191a23]">
      <HeroSection />
      <PainPointCardsSection />
      <ScreenshotSection />
      <DiagnosisSection />
      <FAQSection />
    </div>
  );
}
