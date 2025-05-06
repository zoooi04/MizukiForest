<%-- 
    Document   : index
    Created on : Mar 6, 2025, 10:41:03 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/media/images/mizuki.png">
        <title>Mizuki's Forest - Grow Focus & Well-being</title>
    </head>
    <body>
        <main>
            <section class="hero">
                <div class="container">
                    <h1>Grow your <span class="highlight">focus</span> and <span class="highlight">well-being</span> in Mizuki's Forest</h1>
                    <p>Transform your productivity with a mindful approach that nurtures your mental health and grows real trees on Earth</p>
                    <a href="<%= request.getContextPath()%>/view/general/login.jsp" class="btn">Start Growing Today</a>
                    <a href="#features" class="btn btn-outline">Explore Features</a>

                    <div class="forest-creatures">
                        <img src="<%= request.getContextPath()%>/media/images/item/mythic_fox.png" alt="Forest spirit" class="creature">
                        <img src="<%= request.getContextPath()%>/media/images/item/emerald_owl.png" alt="Forest guardian" class="creature">
                        <img src="<%= request.getContextPath()%>/media/images/item/crystal_deer.png" alt="Forest friend" class="creature">
                    </div>
                </div>
            </section>

            <section class="features" id="features">
                <div class="container">
                    <h2>Nurture your <span class="highlight">productivity</span> and <span class="highlight">mental health</span></h2>
                    <p>Mizuki's Forest combines powerful tools to help you stay focused, track your journey, and care for your well-being</p>

                    <div class="feature-grid">
                        <div class="feature-card">
                            <div class="feature-icon">🍅</div>
                            <h3>Focus Forest</h3>
                            <p>Use the Pomodoro technique to improve focus. For every completed session, we plant a virtual tree in your forest - and real trees on Earth!</p>
                        </div>

                        <div class="feature-card">
                            <div class="feature-icon">📔</div>
                            <h3>Mindful Journal</h3>
                            <p>Track your thoughts, feelings and progress with our guided daily journal entries that help build self-awareness.</p>
                        </div>

                        <div class="feature-card">
                            <div class="feature-icon">👥</div>
                            <h3>Support Circle</h3>
                            <p>Connect with certified therapists and a supportive community in our mindfully moderated forums.</p>
                        </div>

                        <div class="feature-card">
                            <div class="feature-icon">🎵</div>
                            <h3>Ambient Soundscapes</h3>
                            <p>Enhance your focus with curated forest sounds, ambient music, and calming melodies designed for productivity.</p>
                        </div>
                    </div>
                </div>
            </section>

            <section class="app-preview">
                <div class="container">
                    <h2>Watch your <span class="highlight">forest grow</span> with every focused session</h2>
                    <p>Our beautiful interface helps you visualize your progress while making productivity feel like a meaningful adventure</p>

                    <img src="<%= request.getContextPath()%>/media/images/screenshot_forest.jpg" alt="Mizuki's Forest App Screenshot" class="app-screenshot">

                    <div class="feature-boxes">
                        <div class="feature-box">
                            <h3>Plant Trees</h3>
                            <p>Every completed focus session plants a virtual tree and contributes to real-world reforestation projects</p>
                        </div>

                        <div class="feature-box">
                            <h3>Track Progress</h3>
                            <p>Visual growth of your forest shows your cumulative focus time and achievements</p>
                        </div>

                        <div class="feature-box">
                            <h3>Forest Friends</h3>
                            <p>Unlock adorable forest spirits as companions on your productivity journey</p>
                        </div>

                        <div class="feature-box">
                            <h3>Daily Reflections</h3>
                            <p>Journal prompts help you process thoughts and improve mental clarity</p>
                        </div>
                    </div>
                </div>
            </section>

            <section class="testimonials-section">
                <div class="container">
                    <h2>How Mizuki's Forest <span class="highlight">transforms lives</span></h2>
                    <p>Join thousands of users who have improved their focus and well-being</p>

                    <div class="testimonials">
                        <div class="testimonial-card">
                            <div class="stars">★★★★★</div>
                            <p>"Mizuki's Forest helped me manage my ADHD symptoms better than any other app. The combination of Pomodoro, journaling and therapy support is exactly what I needed."</p>
                            <p><strong>— Ava T.</strong></p>
                        </div>

                        <div class="testimonial-card">
                            <div class="stars">★★★★★</div>
                            <p>"I love how this combines productivity with mental health. The forest visualization makes me want to stay focused, and the journal helps me process my thoughts."</p>
                            <p><strong>— Marcus J.</strong></p>
                        </div>

                        <div class="testimonial-card">
                            <div class="stars">★★★★★</div>
                            <p>"The ambient sounds are perfect for my workflow, and knowing I'm helping plant real trees gives me extra motivation. Plus, the community is so supportive!"</p>
                            <p><strong>— Yuki S.</strong></p>
                        </div>
                    </div>
                </div>
            </section>

            <section class="faq-section">
                <div class="container">
                    <h2>Frequently Asked Questions</h2>

                    <div class="faq-container">
                        <div class="faq-item">
                            <div class="faq-question">
                                How does the Pomodoro technique work?
                            </div>
                            <div class="faq-answer">
                                <p>The Pomodoro Technique is a time management method that uses a timer to break work into intervals, traditionally 25 minutes in length, separated by short breaks. In Mizuki's Forest, each completed Pomodoro session plants a tree in your virtual forest and contributes to real-world tree planting.</p>
                            </div>
                        </div>

                        <div class="faq-item">
                            <div class="faq-question">
                                Do you really plant trees?
                            </div>
                            <div class="faq-answer">
                                <p>Yes! We partner with verified reforestation organizations. For every 10 virtual trees grown in our users' forests, we plant one real tree on Earth. You can track the impact of our community in the app.</p>
                            </div>
                        </div>

                        <div class="faq-item">
                            <div class="faq-question">
                                How does the therapist feature work?
                            </div>
                            <div class="faq-answer">
                                <p>Our Premium tier provides access to licensed therapists who can offer guidance through secure messaging. They provide support for productivity challenges, anxiety, and establishing healthy habits. This is not a replacement for traditional therapy but serves as complementary support.</p>
                            </div>
                        </div>

                        <div class="faq-item">
                            <div class="faq-question">
                                Is my journal data private?
                            </div>
                            <div class="faq-answer">
                                <p>Absolutely. Your journal entries are encrypted and private by default. You can optionally choose to share specific entries with your therapist or post anonymized reflections to the community forum, but this is always under your control.</p>
                            </div>
                        </div>
                    </div>

                    <script>
                        // Add JavaScript for the FAQ dropdown functionality
                        document.addEventListener('DOMContentLoaded', function () {
                            const faqItems = document.querySelectorAll('.faq-item');

                            faqItems.forEach(item => {
                                const question = item.querySelector('.faq-question');

                                question.addEventListener('click', () => {
                                    // Close all other items
                                    faqItems.forEach(otherItem => {
                                        if (otherItem !== item && otherItem.classList.contains('active')) {
                                            otherItem.classList.remove('active');
                                        }
                                    });

                                    // Toggle current item
                                    item.classList.toggle('active');
                                });
                            });

                            // Open the first FAQ item by default
                            if (faqItems.length > 0) {
                                faqItems[0].classList.add('active');
                            }
                        });
                    </script>
                </div>
            </section>

            <section class="cta-section">
                <div class="container">
                    <h2>Join Mizuki's Forest <span class="highlight">today</span></h2>
                    <p>Start your journey toward better focus, productivity, and mental well-being while helping the planet</p>
                    <a href="#" class="btn">Begin Your Forest Journey</a>
                    <p class="small">Available on iOS, Android, and Pc's Web</p>
                </div>
            </section>
        </main>

        <footer class="footer">
            <div class="container">
                <p>&copy; 2025 Mizuki's Forest. All rights reserved.</p>
                <p>
                    <a href="view/general/privacy.jsp">Privacy Policy</a> • 
                    <a href="view/general/terms.jsp">Terms of Service</a> •
                    <a href="#features">About</a>
                </p>
            </div>
        </footer>


        <style>
            body {
                font-family: 'Inter', sans-serif;
                background-color: #242424;
                color: #f0f0f0;
                margin: 0;
                padding: 0;
                line-height: 1.6;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            section {
                padding: 60px 0;
                text-align: center;
            }

            h1, h2, h3 {
                font-weight: 700;
            }

            h1 {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }

            h2 {
                font-size: 2rem;
                margin-bottom: 1.5rem;
            }

            p {
                font-size: 1.1rem;
                margin-bottom: 1.5rem;
            }

            .highlight {
                color: #8bc34a;
            }

            .btn {
                display: inline-block;
                background-color: #8bc34a;
                color: #242424;
                padding: 10px 20px;
                border-radius: 30px;
                text-decoration: none;
                font-weight: 600;
                margin: 10px;
                transition: all 0.3s;
            }

            .btn:hover {
                background-color: #a4d967;
                transform: translateY(-2px);
            }

            .btn-outline {
                background-color: transparent;
                border: 2px solid #8bc34a;
                color: #8bc34a;
            }

            .btn-outline:hover {
                background-color: #8bc34a;
                color: #242424;
            }

            .feature-grid {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 30px;
                margin-top: 40px;
            }

            .feature-card {
                background-color: #333333;
                border-radius: 15px;
                padding: 30px;
                width: 260px;
                text-align: center;
                transition: all 0.3s;
            }

            .feature-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
            }

            .feature-icon {
                width: 80px;
                height: 80px;
                background-color: #8bc34a33;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 0 auto 20px;
                font-size: 2rem;
                color: #8bc34a;
            }

            .app-screenshot {
                max-width: 80%;
                border-radius: 15px;
                margin: 40px auto;
                box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5);
            }

            .testimonials {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 30px;
                margin-top: 40px;
            }

            .testimonial-card {
                background-color: #333333;
                border-radius: 15px;
                padding: 30px;
                width: 300px;
                text-align: left;
            }

            .stars {
                color: #ffeb3b;
                font-size: 1.2rem;
                margin-bottom: 10px;
            }

            .footer {
                background-color: #1a1a1a;
                padding: 40px 0;
                text-align: center;
            }

            .footer a {
                color: #8bc34a;
                text-decoration: none;
            }

            .footer a:hover {
                text-decoration: underline;
            }

            .faq-container {
                max-width: 800px;
                margin: 40px auto;
            }

            .faq-item {
                background-color: #333333;
                border-radius: 10px;
                margin-bottom: 15px;
                overflow: hidden;
            }

            .faq-question {
                background-color: #404040;
                padding: 15px 20px;
                cursor: pointer;
                font-weight: 600;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .faq-question::after {
                content: '+';
                font-size: 1.5rem;
                transition: transform 0.3s ease;
            }

            .faq-item.active .faq-question::after {
                transform: rotate(45deg);
            }

            .faq-answer {
                padding: 0 20px;
                max-height: 0;
                overflow: hidden;
                text-align: left;
                transition: all 0.3s ease;
            }

            .faq-item.active .faq-answer {
                padding: 0 20px 20px;
                max-height: 500px;
            }

            .forest-creatures {
                display: flex;
                justify-content: center;
                gap: 40px;
                margin: 40px 0;
            }

            .creature {
                width: 100px;
                height: 100px;
                transition: all 0.3s;
            }

            .creature:hover {
                transform: translateY(-10px) rotate(5deg);
            }

            .mobile-mockups {
                display: flex;
                justify-content: center;
                gap: 30px;
                flex-wrap: wrap;
                margin: 40px 0;
            }

            .mobile-mockup {
                max-width: 280px;
                border-radius: 20px;
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.4);
            }

            .feature-box {
                background-color: #333333;
                border-radius: 15px;
                padding: 20px;
                margin: 10px;
                width: 220px;
                text-align: center;
                transition: all 0.3s;
            }

            .feature-box:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
            }

            .feature-boxes {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 15px;
                margin-top: 30px;
            }
        </style>
    </body>
</html>