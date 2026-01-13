import type { Metadata } from "next";
import "./globals.css";
import { GNB } from "@/widgets/gnb";
import { MemberProvider } from "@/entities/member";

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
        <MemberProvider>
          <GNB />
          <main className="pt-[56px]">{children}</main>
        </MemberProvider>
      </body>
    </html>
  );
}