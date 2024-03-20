using PdfSharp.Drawing;
using PdfSharp.Pdf;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class PDFGeneratorService
    {
        private readonly string[] labels;
        private readonly string[] values;
        private readonly string title;

        public PDFGeneratorService(string title, string[] labels, string[] values)
        {
            this.title = title;
            this.labels = labels;
            this.values = values;
        }

        public void GeneratePDF(string filename)
        {
            PdfDocument document = new PdfDocument();
            document.Info.Title = title;

            PdfPage page = document.AddPage();
            XGraphics gfx = XGraphics.FromPdfPage(page);

            XFont titleFont = new XFont("Verdana", 14, XFontStyle.Bold);
            XFont headerFont = new XFont("Verdana", 12, XFontStyle.Bold);
            XFont contentFont = new XFont("Verdana", 10);

            double yPos = 40;
            double indent = 20;

            gfx.DrawString(title, titleFont, XBrushes.Black, new XRect(0, yPos, page.Width, page.Height), XStringFormats.TopCenter);
            yPos += 30;

            for (int i = 0; i < labels.Length; i++)
            {
                gfx.DrawString(labels[i], headerFont, XBrushes.Black, new XRect(indent, yPos, page.Width, page.Height), XStringFormats.TopLeft);
                gfx.DrawString(values[i], contentFont, XBrushes.Black, new XRect(indent * 2, yPos + 20, page.Width, page.Height), XStringFormats.TopLeft);
                yPos += 40;
            }

            document.Save(filename);
            Process.Start(new ProcessStartInfo(filename) { UseShellExecute = true });
        }
    }
}
