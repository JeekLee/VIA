import type { Metadata } from "next";
import "./globals.css";
import { GNB } from "@/widgets/gnb";

export const metadata: Metadata = {
  title: "VIA",
  description: "VIA Frontend",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body>
        <GNB />
        <main className="pt-[56px]">{children}</main>
      </body>
    </html>
  );
}