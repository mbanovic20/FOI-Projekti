using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class EmailService
    {
        private string fromMail = "thesnackalchemist2023@gmail.com";
        private string fromPassword = "dxtylhsnamqyjfex";
        MailMessage Message = new MailMessage();

        public EmailService(string subject, string body, string mailTo)
        {
            Message.From = new MailAddress(fromMail);
            Message.Subject = subject;
            Message.To.Add(new MailAddress(mailTo));
            Message.Body = "<html><body>" + body + "</body></html>";
            Message.IsBodyHtml = true;

            var smtpClient = new SmtpClient("smtp.gmail.com")
            {
                Port = 587,
                Credentials = new NetworkCredential(fromMail, fromPassword),
                EnableSsl = true
            };

            smtpClient.Send(Message);
        }
    }
}
