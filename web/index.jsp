<%-- 
    Document   : index
    Created on : Mar 6, 2025, 10:41:03 AM
    Author     : JiaQuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <jsp:include page="${request.contextPath}/shared/commonHeader.jsp"/>
    <body>

        <%request.setAttribute("pageTitle", "Home");%>
        <jsp:include page="shared/title.jsp"/>
        <%@ include file="shared/header.jsp" %>

        <main class="container mt-5 pt-5">
            <button id="themeToggle">Change Theme Mode</button>


            <h1>Just For Testing - Complete Story Summary</h1>

            <h2>Humanity Behind the Walls</h2>
            <p>For over a century, humanity has lived in fear of the monstrous humanoid creatures known as <span class="highlight">Titans</span>. To protect themselves, they built three massive walls:</p>
            <ul>
                <li><strong>Wall Maria</strong> – The outermost wall, first to be breached.</li>
                <li><strong>Wall Rose</strong> – The middle wall, holding most of humanity.</li>
                <li><strong>Wall Sheena</strong> – The innermost and most protected area, where the royal family resides.</li>
            </ul>
            <p>People live in relative peace, but most do not know the true history of the world.</p>

            <h2>The Fall of Wall Maria (Year 845)</h2>
            <p>Their peace is shattered when a <span class="highlight">Colossal Titan</span> appears and kicks a hole in <strong>Wall Maria</strong>. As Titans flood in, chaos erupts. Amidst the destruction:</p>
            <ul>
                <li><strong>Eren Yeager</strong> watches in horror as his mother is devoured by a Titan.</li>
                <li>Mikasa and Armin escape to safety.</li>
                <li>The survivors flee to <strong>Wall Rose</strong>.</li>
            </ul>
            <p>Fueled by hatred and vengeance, Eren vows to eradicate all Titans and joins the military.</p>

            <h2>Training and the Discovery of Titan Shifters</h2>
            <p>Eren, Mikasa, and Armin enlist in the military, undergoing intense training. They graduate as some of the top recruits and join the <span class="highlight">Survey Corps</span>, an elite unit that fights Titans outside the walls.</p>
            <p>During their first major mission, Eren is swallowed by a Titan but miraculously transforms into one himself. This shocking revelation sparks controversy within the military, but his abilities become a crucial asset in battle.</p>
            <p>Later, they uncover that <strong>some of their own comrades are Titan Shifters</strong>—humans who can transform into Titans at will:</p>
            <ul>
                <li><span class="highlight">Annie Leonhart</span> is revealed to be the <strong>Female Titan</strong>.</li>
                <li><span class="highlight">Reiner Braun</span> is the <strong>Armored Titan</strong>, and <span class="highlight">Bertholdt Hoover</span> is the <strong>Colossal Titan</strong>.</li>
            </ul>
            <p>They betray humanity, revealing their true allegiance to an unknown enemy.</p>

            <h2>The Truth Behind the Walls</h2>
            <p>The Survey Corps investigates and discovers:</p>
            <ul>
                <li>The walls are built from hardened Titans.</li>
                <li>The <strong>Reiss Family</strong> (royal family) possesses the power to control Titans.</li>
                <li>Humanity inside the walls is being <strong>deceived</strong> about their true history.</li>
            </ul>
            <p>During a rebellion, Historia Reiss takes the throne, overthrowing the corrupt government.</p>

            <h2>Eren’s Basement – The True History</h2>
            <p>After retaking Wall Maria, Eren finally reaches his old home and unlocks his father’s basement, revealing the <strong>hidden truth</strong>:</p>
            <ul>
                <li>Humanity inside the walls are descendants of the <strong>Eldian race</strong>.</li>
                <li>The Titans are actually transformed humans, used as weapons.</li>
                <li>The real enemy is the <span class="highlight">Marleyan Empire</span>, which seeks to oppress Eldians.</li>
                <li>The world beyond the walls is vast and technologically advanced.</li>
            </ul>
            <p>This revelation changes everything, as Eren realizes that the true battle lies beyond the ocean.</p>

            <h2>The War Between Marley and Paradis</h2>
            <p>Years later, Eren leads an attack on the <strong>Marleyan capital</strong>, killing thousands. He acquires the <strong>War Hammer Titan</strong> and declares war on the entire world.</p>
            <p>Meanwhile, the Survey Corps becomes divided:</p>
            <ul>
                <li>Eren forms the <span class="highlight">Yeagerists</span>, a radical group supporting his plan.</li>
                <li>Armin and Mikasa struggle to stop Eren’s reckless actions.</li>
            </ul>
            <p>As tensions rise, Eren activates the <strong>Rumbling</strong>, unleashing millions of Colossal Titans to destroy the world.</p>

            <h2>The Final Battle</h2>
            <p>With the world at stake, former enemies unite:</p>
            <ul>
                <li>Mikasa, Armin, Reiner, and others team up with Marley to stop Eren.</li>
                <li>A final showdown takes place on Eren’s <strong>massive Founding Titan form</strong>.</li>
                <li>Mikasa delivers the final blow, <strong>killing Eren</strong> and ending the Rumbling.</li>
            </ul>

            <h2>Aftermath and Conclusion</h2>
            <p>With Eren’s death, the power of the Titans disappears forever. The world struggles to rebuild, but tensions remain between Eldians and Marleyans. In the end, humanity moves forward, uncertain of what the future holds.</p>

        </main>

    </body>
    <script src="js/mizukibase.js"></script>
</html>

