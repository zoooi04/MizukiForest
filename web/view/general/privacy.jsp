<%-- 
    Document   : condition
    Created on : May 4, 2025, 11:04:51 AM
    Author     : JiaQuann
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html data-theme="mizuki_dark" class="dark">
    <%request.setAttribute("pageTitle", "Privacy Policy");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="../../shared/notLoginHeader.jsp"/>
    <style>
        /* Privacy Policy Page Styles */
        .pp-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem 1rem;
        }
        
        .pp-card {
            max-width: 900px;
            margin: 0 auto;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }
        
        .pp-title {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 1.5rem;
            text-align: center;
        }
        
        .pp-updated {
            margin-bottom: 2rem;
            text-align: right;
            font-style: italic;
        }
        
        .pp-section {
            margin-bottom: 2rem;
        }
        
        .pp-section-title {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 1rem;
        }
        
        .pp-content {
            line-height: 1.6;
        }
        
        .pp-list {
            list-style-type: disc;
            padding-left: 2rem;
            margin-top: 1rem;
        }
        
        .pp-list li {
            margin-bottom: 0.5rem;
        }
        
        .pp-contact {
            border-top: 1px solid rgba(128, 128, 128, 0.2);
            margin-top: 2rem;
            padding-top: 1.5rem;
        }
    </style>
    <body>
        <div class="pp-container">
            <div class="pp-card">
                <h1 class="pp-title">Privacy Policy</h1>
                
                <div class="pp-content">
                    <p class="pp-updated">Last Updated: May 4, 2025</p>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">1. Introduction</h2>
                        <p>Welcome to our Privacy Policy. This Privacy Policy explains how we collect, use, disclose, and safeguard your information when you visit our website and use our services. We respect your privacy and are committed to protecting your personal data. Please read this privacy policy carefully to understand our policies and practices regarding your information.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">2. Information We Collect</h2>
                        <p>We may collect several types of information from and about users of our website, including:</p>
                        <ul class="pp-list">
                            <li><strong>Personal Data:</strong> Personal data refers to any information that identifies an individual such as your name, email address, postal address, phone number, and other similar information that you provide when registering or using our services.</li>
                            <li><strong>Usage Data:</strong> We may automatically collect information about how you interact with our site, including pages viewed, time spent on pages, navigation paths, as well as information about your computer and internet connection, such as your IP address, operating system, and browser type.</li>
                            <li><strong>Cookies Data:</strong> We use cookies and similar tracking technologies to track activity on our Service and hold certain information. Cookies are files with small amount of data which may include an anonymous unique identifier.</li>
                        </ul>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">3. How We Use Your Information</h2>
                        <p>We may use the information we collect from you for various purposes, including to:</p>
                        <ul class="pp-list">
                            <li>Provide, maintain, and improve our services</li>
                            <li>Process your transactions and send related information</li>
                            <li>Send administrative information, such as updates, security alerts, and support messages</li>
                            <li>Respond to your comments, questions, and requests</li>
                            <li>Communicate with you about products, services, offers, and events</li>
                            <li>Monitor and analyze trends, usage, and activities in connection with our website</li>
                            <li>Detect, prevent, and address technical issues, fraud, and illegal activities</li>
                            <li>Personalize and improve your experience on our website</li>
                        </ul>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">4. Disclosure of Your Information</h2>
                        <p>We may disclose your personal information in the following circumstances:</p>
                        <ul class="pp-list">
                            <li><strong>Service Providers:</strong> We may share your information with third-party vendors, service providers, contractors, or agents who perform services for us or on our behalf.</li>
                            <li><strong>Business Transfers:</strong> If we are involved in a merger, acquisition, or sale of all or a portion of our assets, your information may be transferred as part of that transaction.</li>
                            <li><strong>Legal Requirements:</strong> We may disclose your information where required to do so by law or in response to valid requests by public authorities.</li>
                            <li><strong>Protection:</strong> We may disclose information to protect the rights, property, or safety of our company, our users, or others.</li>
                        </ul>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">5. Data Security</h2>
                        <p>We have implemented appropriate technical and organizational security measures designed to protect the security of any personal information we process. However, please also remember that we cannot guarantee that the internet itself is 100% secure. Although we will do our best to protect your personal information, transmission of personal information to and from our website is at your own risk.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">6. Your Data Protection Rights</h2>
                        <p>Depending on your location, you may have the following data protection rights:</p>
                        <ul class="pp-list">
                            <li>The right to access, update, or delete the information we have on you</li>
                            <li>The right of rectification - the right to have your information corrected if that information is inaccurate or incomplete</li>
                            <li>The right to object to our processing of your personal data</li>
                            <li>The right of restriction - the right to request that we restrict the processing of your personal information</li>
                            <li>The right to data portability - the right to be provided with a copy of your personal data in a structured, machine-readable format</li>
                            <li>The right to withdraw consent at any time where we relied on your consent to process your personal information</li>
                        </ul>
                        <p>To exercise any of these rights, please contact us using the contact information provided below.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">7. Children's Privacy</h2>
                        <p>Our service is not directed to children under the age of 13 (or such age as required by local law), and we do not knowingly collect personal information from children. If we learn we have collected or received personal information from a child without verification of parental consent, we will delete that information. If you believe we might have any information from or about a child, please contact us.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">8. Third-Party Websites</h2>
                        <p>Our website may contain links to third-party websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services. We encourage you to review the privacy policy of every site you visit.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">9. Cookie Policy</h2>
                        <p>Cookies are small pieces of text sent to your browser when you visit our website. We use cookies to help us understand and save your preferences for future visits, keep track of advertisements, and compile aggregate data about site traffic and site interaction.</p>
                        <p>You can choose to have your computer warn you each time a cookie is being sent, or you can choose to turn off all cookies through your browser settings. However, if you disable cookies, some features of our service may not function properly.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">10. International Data Transfers</h2>
                        <p>Your information may be transferred to — and maintained on — computers located outside of your state, province, country, or other governmental jurisdiction where the data protection laws may differ from those of your jurisdiction. If you are located outside of [Your Country] and choose to provide information to us, please note that we transfer the data to [Your Country] and process it there.</p>
                    </section>
                    
                    <section class="pp-section">
                        <h2 class="pp-section-title">11. Changes to This Privacy Policy</h2>
                        <p>We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page and updating the "Last Updated" date at the top of this Privacy Policy. You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.</p>
                    </section>
                    
                    <section class="pp-section pp-contact">
                        <h2 class="pp-section-title">12. Contact Us</h2>
                        <p>If you have any questions about this Privacy Policy, please contact us at:</p>
                        <p>[Your Company Name]<br>
                        [Your Address]<br>
                        [Your Email Address]<br>
                        [Your Phone Number]</p>
                    </section>
                </div>
            </div>
        </div>
    </body>
</html>