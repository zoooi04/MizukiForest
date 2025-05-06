<%-- 
    Document   : termncondition
    Created on : May 4, 2025, 11:04:16 AM
    Author     : JiaQuann
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html data-theme="mizuki_dark" class="dark">
    <%request.setAttribute("pageTitle", "Terms of Service");%>
    <jsp:include page="../../shared/title.jsp"/>
    <jsp:include page="../../shared/notLoginHeader.jsp"/>
    <style>
        /* Terms of Service Page Styles */
        .tos-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem 1rem;
        }
        
        .tos-card {
            max-width: 900px;
            margin: 0 auto;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }
        
        .tos-title {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 1.5rem;
            text-align: center;
        }
        
        .tos-updated {
            margin-bottom: 2rem;
            text-align: right;
            font-style: italic;
        }
        
        .tos-section {
            margin-bottom: 2rem;
        }
        
        .tos-section-title {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 1rem;
        }
        
        .tos-content {
            line-height: 1.6;
        }
        
        .tos-list {
            list-style-type: disc;
            padding-left: 2rem;
            margin-top: 1rem;
        }
        
        .tos-list li {
            margin-bottom: 0.5rem;
        }
        
        .tos-contact {
            border-top: 1px solid rgba(128, 128, 128, 0.2);
            margin-top: 2rem;
            padding-top: 1.5rem;
        }
    </style>
    <body>
        <div class="tos-container">
            <div class="tos-card">
                <h1 class="tos-title">Terms of Service</h1>
                
                <div class="tos-content">
                    <p class="tos-updated">Last Updated: May 4, 2025</p>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">1. Introduction</h2>
                        <p>Welcome to our service. By accessing or using our website, services, applications, products and content (collectively, "Services"), you agree to be bound by these Terms of Service ("Terms"). These Terms affect your legal rights and obligations, so if you do not agree to these Terms, please do not use our Services.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">2. Definitions</h2>
                        <p>Throughout these Terms, we may use certain words or phrases, and it is important that you understand the meaning of them. The list is not all-encompassing and no definition should be considered exhaustive:</p>
                        <ul class="tos-list">
                            <li>"Service" refers to the website, platform, and services that we provide</li>
                            <li>"We," "us," and "our" refer to our company</li>
                            <li>"User," "you," and "your" refer to you, the person who is accessing our Service</li>
                            <li>"Content" refers to any text, images, graphics, photos, audio, video, or other materials you may encounter on our Service</li>
                            <li>"Account" refers to the personal account you may create to access certain features of our Service</li>
                        </ul>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">3. Eligibility</h2>
                        <p>By using our Services, you represent and warrant that:</p>
                        <ul class="tos-list">
                            <li>You are at least 13 years of age</li>
                            <li>You have the right, authority, and capacity to enter into these Terms</li>
                            <li>You will use our Services in accordance with these Terms</li>
                            <li>You are not prohibited from using our Services under any applicable laws</li>
                        </ul>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">4. Account Registration and Security</h2>
                        <p>To access certain features of our Service, you may need to create an account. When you create an account, you agree to:</p>
                        <ul class="tos-list">
                            <li>Provide accurate, current, and complete information</li>
                            <li>Maintain and promptly update your account information</li>
                            <li>Maintain the security of your account and password</li>
                            <li>Not share your account credentials with any third party</li>
                            <li>Notify us immediately of any unauthorized use of your account</li>
                        </ul>
                        <p class="mt-2">You are solely responsible for all activities that occur under your account. We reserve the right to suspend or terminate your account if any information provided proves to be inaccurate, not current, or incomplete.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">5. User Content</h2>
                        <p>Our Service may allow you to post, upload, publish, submit, or transmit content ("User Content"). By providing User Content, you grant us a worldwide, non-exclusive, royalty-free, fully paid, transferable, and sublicensable license to use, reproduce, modify, adapt, publish, translate, create derivative works from, distribute, and display such User Content in any media formats and through any media channels.</p>
                        <p>You represent and warrant that:</p>
                        <ul class="tos-list">
                            <li>You own or have the necessary rights to the User Content you submit</li>
                            <li>The User Content does not infringe upon the intellectual property rights of any third party</li>
                            <li>The User Content does not violate any applicable laws or regulations</li>
                            <li>The User Content does not contain any defamatory, obscene, offensive, or otherwise objectionable material</li>
                        </ul>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">6. Prohibited Activities</h2>
                        <p>You agree not to engage in any of the following prohibited activities:</p>
                        <ul class="tos-list">
                            <li>Violating any applicable laws, regulations, or third-party rights</li>
                            <li>Using our Services for any illegal or unauthorized purpose</li>
                            <li>Posting or transmitting viruses, malware, or other malicious code</li>
                            <li>Attempting to gain unauthorized access to our systems or networks</li>
                            <li>Impersonating another person or entity</li>
                            <li>Harassing, intimidating, or threatening other users</li>
                            <li>Collecting or harvesting user data without permission</li>
                            <li>Using automated methods to access or use our Services without our permission</li>
                            <li>Interfering with or disrupting our Services</li>
                        </ul>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">7. Intellectual Property Rights</h2>
                        <p>Our Service and its original content, features, and functionality are owned by us and are protected by international copyright, trademark, patent, trade secret, and other intellectual property or proprietary rights laws.</p>
                        <p class="mt-2">You may not copy, modify, distribute, sell, or lease any part of our Services or included software, nor may you reverse engineer or attempt to extract the source code of that software, unless applicable laws prohibit these restrictions or you have our written permission.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">8. Privacy Policy</h2>
                        <p>Our Privacy Policy describes how we handle the information you provide to us when you use our Services. By using our Services, you agree that we can use such information in accordance with our Privacy Policy.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">9. Limitation of Liability</h2>
                        <p>To the maximum extent permitted by applicable law, we shall not be liable for any indirect, incidental, special, consequential, or punitive damages, or any loss of profits or revenues, whether incurred directly or indirectly, or any loss of data, use, goodwill, or other intangible losses, resulting from:</p>
                        <ul class="tos-list">
                            <li>Your access to or use of or inability to access or use our Services</li>
                            <li>Any conduct or content of any third party on our Services</li>
                            <li>Any content obtained from our Services</li>
                            <li>Unauthorized access, use, or alteration of your transmissions or content</li>
                        </ul>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">10. Disclaimer of Warranties</h2>
                        <p>Our Services are provided "as is" and "as available" without warranties of any kind, either express or implied, including, but not limited to, implied warranties of merchantability, fitness for a particular purpose, title, and non-infringement. We do not warrant that our Services will be uninterrupted, error-free, or completely secure.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">11. Indemnification</h2>
                        <p>You agree to defend, indemnify, and hold us harmless from and against any claims, liabilities, damages, losses, and expenses, including, without limitation, reasonable legal and accounting fees, arising out of or in any way connected with your access to or use of our Services or your violation of these Terms.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">12. Changes to Terms</h2>
                        <p>We reserve the right to modify or replace these Terms at any time. If a revision is material, we will provide at least 30 days' notice prior to any new terms taking effect. What constitutes a material change will be determined at our sole discretion.</p>
                        <p class="mt-2">By continuing to access or use our Services after any revisions become effective, you agree to be bound by the revised terms. If you do not agree to the new terms, you are no longer authorized to use our Services.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">13. Governing Law</h2>
                        <p>These Terms shall be governed and construed in accordance with the laws of [Your Jurisdiction], without regard to its conflict of law provisions.</p>
                        <p class="mt-2">Our failure to enforce any right or provision of these Terms will not be considered a waiver of those rights. If any provision of these Terms is held to be invalid or unenforceable by a court, the remaining provisions of these Terms will remain in effect.</p>
                    </section>
                    
                    <section class="tos-section">
                        <h2 class="tos-section-title">14. Termination</h2>
                        <p>We may terminate or suspend your account and access to our Services immediately, without prior notice or liability, under our sole discretion, for any reason whatsoever, including, without limitation, if you breach these Terms.</p>
                        <p class="mt-2">Upon termination, your right to use our Services will immediately cease. If you wish to terminate your account, you may simply discontinue using our Services or delete your account if such functionality is available.</p>
                    </section>
                    
                    <section class="tos-section tos-contact">
                        <h2 class="tos-section-title">15. Contact Information</h2>
                        <p>If you have any questions about these Terms, please contact us at:</p>
                        <p class="mt-2">[Your Company Name]<br>
                        Mizuki;s Aisa Bathhouse Coop.<br>
                        mizuki@forest.com</p>
                    </section>
                </div>
            </div>
        </div>
    </body>
</html>
