package fpt.com.fresher.recruitmentmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("Hieu2895@gmail.com", "HieuCM2 Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<center>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" id=\"bodyTable\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; height: 100%; margin: 0; padding: 0; width:\n" +
                " 100%\" width=\"100%\">\n" +
                "            <tr>\n" +
                "                <td align=\"center\" id=\"bodyCell\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-top: 0;\n" +
                " height: 100%; margin: 0; padding: 0; width: 100%\" valign=\"top\">\n" +
                "                    <!-- BEGIN TEMPLATE // -->\n" +
                "                    <!--[if gte mso 9]>\n" +
                "              <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:600px;\">\n" +
                "                <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "                  <![endif]-->\n" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"templateContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; max-width:\n" +
                " 600px; border: 0\" width=\"100%\">\n" +
                "                        <tr>\n" +
                "                            <td id=\"templatePreheader\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 16px; padding-bottom: 8px\" valign=\"top\">\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                                   \n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td id=\"templateHeader\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #f7f7ff;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 16px; padding-bottom: 0\" valign=\"top\">\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnImageBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                                    <tbody class=\"mcnImageBlockOuter\">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mcnImageBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding:0px\" valign=\"top\">\n" +
                "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                    class=\"mcnImageContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td class=\"mcnImageContent\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-right: 0px;\n" +
                " padding-left: 0px; padding-top: 0; padding-bottom: 0; text-align:center;\" valign=\"top\">\n" +
                "                                                                <a class=\"\" href=\"https://www.lingoapp.com\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color:\n" +
                " #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\" title=\"\">\n" +
                "                                                                    <a class=\"\" href=\"https://www.lingoapp.com/\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color:\n" +
                " #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\" title=\"\">\n" +
                "                                                                        <img align=\"center\" alt=\"Forgot your password?\"\n" +
                "                                                                            class=\"mcnImage\"\n" +
                "                                                                            src=\"https://static.lingoapp.com/assets/images/email/il-password-reset@2x.png\"\n" +
                "                                                                            style=\"-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                " text-decoration: none; vertical-align: bottom; max-width:1200px; padding-bottom:\n" +
                " 0; display: inline !important; vertical-align: bottom;\" width=\"600\"></img>\n" +
                "                                                                    </a>\n" +
                "                                                                </a>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td id=\"templateBody\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #f7f7ff;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 0; padding-bottom: 0\" valign=\"top\">\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                    <tbody class=\"mcnTextBlockOuter\">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mcnTextBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%\" valign=\"top\">\n" +
                "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                    class=\"mcnTextContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td class=\"mcnTextContent\" style='mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                " color: #2a2a2a; font-family: \"Asap\", Helvetica, sans-serif; font-size: 16px;\n" +
                " line-height: 150%; text-align: center; padding-top:9px; padding-right: 18px;\n" +
                " padding-bottom: 9px; padding-left: 18px;' valign=\"top\">\n" +
                "\n" +
                "                                                                <h1 class=\"null\" style='color: #2a2a2a; font-family: \"Asap\", Helvetica,\n" +
                " sans-serif; font-size: 32px; font-style: normal; font-weight: bold; line-height:\n" +
                " 125%; letter-spacing: 2px; text-align: center; display: block; margin: 0;\n" +
                " padding: 0'><span style=\"text-transform:uppercase\">Forgot</span></h1>\n" +
                "\n" +
                "\n" +
                "                                                                <h2 class=\"null\" style='color: #2a2a2a; font-family: \"Asap\", Helvetica,\n" +
                " sans-serif; font-size: 24px; font-style: normal; font-weight: bold; line-height:\n" +
                " 125%; letter-spacing: 1px; text-align: center; display: block; margin: 0;\n" +
                " padding: 0'><span style=\"text-transform:uppercase\">your password?</span></h2>\n" +
                "\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace:\n" +
                " 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                                    <tbody class=\"mcnTextBlockOuter\">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mcnTextBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%\" valign=\"top\">\n" +
                "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                    class=\"mcnTextContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td class=\"mcnTextContent\" style='mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                " color: #2a2a2a; font-family: \"Asap\", Helvetica, sans-serif; font-size: 16px;\n" +
                " line-height: 150%; text-align: center; padding-top:9px; padding-right: 18px;\n" +
                " padding-bottom: 9px; padding-left: 18px;' valign=\"top\">Not to worry, we got you! Change you a new password.\n" +
                "                                                                <br></br>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnButtonBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                                    <tbody class=\"mcnButtonBlockOuter\">\n" +
                "                                        <tr>\n" +
                "                                            <td align=\"center\" class=\"mcnButtonBlockInner\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " padding-top:18px; padding-right:18px; padding-bottom:18px; padding-left:18px;\" valign=\"top\">\n" +
                "                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnButtonBlock\"\n" +
                "                                                    style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                                    <tbody class=\"mcnButtonBlockOuter\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td align=\"center\" class=\"mcnButtonBlockInner\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " padding-top:0; padding-right:18px; padding-bottom:18px; padding-left:18px;\" valign=\"top\">\n" +
                "                                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                                    class=\"mcnButtonContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " border-collapse: separate !important;border-radius: 48px;background-color:\n" +
                " #F57153;\">\n" +
                "                                                                    <tbody>\n" +
                "                                                                        <tr>\n" +
                "                                                                            <td align=\"center\" class=\"mcnButtonContent\"\n" +
                "                                                                                style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " font-family: 'Asap', Helvetica, sans-serif; font-size: 16px; padding-top:24px;\n" +
                " padding-right:48px; padding-bottom:24px; padding-left:48px;\" valign=\"middle\">\n" +
                "                                                                                <a class=\"mcnButton \" href=\"" + resetPasswordLink + "#\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; display: block; color: #f57153;\n" +
                " font-weight: normal; text-decoration: none; font-weight: normal;letter-spacing:\n" +
                " 1px;line-height: 100%;text-align: center;text-decoration: none;color:\n" +
                " #FFFFFF; text-transform:uppercase;\" target=\"_blank\" title=\"Review Lingo kit\n" +
                " invitation\">Reset password</a>\n" +
                "                                                                            </td>\n" +
                "                                                                        </tr>\n" +
                "                                                                    </tbody>\n" +
                "                                                                </table>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnImageBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                    <tbody class=\"mcnImageBlockOuter\">\n" +
                "                                        <tr>\n" +
                "                                            <td class=\"mcnImageBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding:0px\" valign=\"top\">\n" +
                "                                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                    class=\"mcnImageContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                        <tr>\n" +
                "                                                            <td class=\"mcnImageContent\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-right: 0px;\n" +
                " padding-left: 0px; padding-top: 0; padding-bottom: 0; text-align:center;\" valign=\"top\"></td>\n" +
                "                                                        </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                       \n" +
                "        </table>\n" +
                "        <!--[if gte mso 9]>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            <![endif]-->\n" +
                "        <!-- // END TEMPLATE -->\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "    </center>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);

    }
}
