-- USERS Table, andrew update: only first 3 record is real life email value...
INSERT INTO USERS (USERID, USERNAME, USERPW, USEREMAIL, USERBIRTHDAY, COINS, EXP, USERLEVEL, LOGINSTREAK, DIARYVISIBILITY, FORESTVISIBILITY, ISDELETED, USERIMAGE) VALUES 
('U2500001', 'Andrew Pheng', 'Andrew@123', 'andrewwwpqj@gmail.com', '1980-12-24', 936, 688, 3, 1, TRUE, TRUE, FALSE, NULL),
('U2500002', 'Huai Ern', 'Huaiern@123', '007kidlolipop@gmail.com', '2002-09-28', 880, 772, 1, 1, TRUE, TRUE, FALSE, NULL),
('U2500003', 'Jian Hui', 'jianhui@123', 'andrewpqj-pm22@student.tarc.edu.my', '1971-05-17', 507, 130, 1, 1, TRUE, TRUE, FALSE, NULL),
('U2500004', 'Choon Chong', 'cccc@123', 'mccallamy@hotmail.com', '1987-05-28', 735, 336, 0, 1, TRUE, TRUE, FALSE, NULL),
('U2500005', 'Jia Quan', '123qwe', 'leejiaquan721@gmail.com', '1980-05-12', 9999, 888, 90, 1, TRUE, TRUE, FALSE, NULL),
('U2500006', 'robert32', 'vqj)C3Fx', 'quinncynthia@rivera.com', '1966-04-02', 998, 456, 8, 1, FALSE, TRUE, TRUE, NULL),
('U2500007', 'nthomas', '@0iAhIqC', 'nancycarroll@yahoo.com', '1989-11-10', 707, 276, 3, 1, FALSE, TRUE, FALSE, NULL),
('U2500008', 'rhodeskelsey', ')5%kRvMQ', 'leahpollard@hotmail.com', '1991-04-20', 673, 538, 5, 1, TRUE, TRUE, FALSE, NULL),
('U2500009', 'ann55', 'rc^f9GzL', 'josejohnson@yahoo.com', '1998-03-22', 651, 613, 6, 1, TRUE, TRUE, FALSE, NULL),
('U2500010', 'sandra66', 'o@e3JizS', 'millerjason@hotmail.com', '2001-03-06', 722, 293, 5, 1, TRUE, FALSE, TRUE, NULL);


-- BADGE Table
INSERT INTO BADGE (BADGEID, BADGENAME, BADGEIMAGE) VALUES
('BA000001', 'GG EZ', NULL),
('BA000002', 'Keyboard The Conqueror', NULL),
('BA000003', 'Ad Astra Abyssosque!', NULL),
('BA000004', 'Run, Forest Run!', NULL);


-- ACHIEVEMENTCATEGORY Table
INSERT INTO ACHIEVEMENTCATEGORY (ACHIEVEMENTCATEGORYID, ACHIEVEMENTCATEGORYNAME, BADGEID) VALUES
('AC000001', 'Productivity', 'BA000001'),
('AC000002', 'Social Interaction', 'BA000002'),
('AC000003', 'Exploration', 'BA000003'),
('AC000004', 'Challenge', 'BA000004');


-- ACHIEVEMENT Table
INSERT INTO ACHIEVEMENT (ACHIEVEMENTID, ACHIEVEMENTCATEGORYID, ACHIEVEMENTNAME, ACHIEVEMENTDESCRIPTION, HIDDEN, ISDELETED) VALUES
('A0000001', 'AC000001', 'Focus Novice', 'Complete 1 focus session', FALSE, FALSE),
('A0000002', 'AC000001', 'Productivity Pro', 'Complete 10 focus sessions', FALSE, FALSE),
('A0000003', 'AC000001', 'Task Master', 'Complete 5 tasks in a day', TRUE, FALSE),
('A0000004', 'AC000001', 'Early Bird', 'Start a focus session before 7 AM', TRUE, FALSE),
('A0000005', 'AC000001', 'Night Owl', 'Start a focus session after 10 PM', TRUE, FALSE),
('A0000006', 'AC000002', 'Social Butterfly', 'Send 10 messages in the forum', FALSE, FALSE),
('A0000007', 'AC000002', 'Su-su-su-super Supportive Soul', 'Upvote 20 comments', FALSE, FALSE),
('A0000008', 'AC000002', 'Community Leader', 'Create a popular thread with 50+ upvotes', TRUE, FALSE),
('A0000009', 'AC000002', 'Empathy Expert', 'Respond to 10 support requests', FALSE, FALSE),
('A0000010', 'AC000002', 'Forum Star', 'Post 5 threads in a week', FALSE, FALSE),
('A0000011', 'AC000003', 'I’ve Already Met My Mona Li-Tree', 'Get the first legendary tree', FALSE, FALSE),
('A0000012', 'AC000003', 'YouSeeBIGTREE', 'Plant the first legendary tree', FALSE, FALSE),
('A0000013', 'AC000003', 'No Tree No Life', 'Fill the land with trees', FALSE, FALSE),
('A0000014', 'AC000003', 'Tree Instrumentality Project', 'There are two islands full of trees', TRUE, FALSE),
('A0000015', 'AC000003', 'To Be Continued....', 'Break too long....', TRUE, FALSE),
('A0000016', 'AC000003', 'One Last Tree', 'Use shovel to remove the dead tree', FALSE, FALSE),
('A0000017', 'AC000003', 'Animal Crossing!', 'Get two animals', FALSE, FALSE),
('A0000018', 'AC000004', 'I mustn''t run away', 'Focus for 30 minutes', FALSE, FALSE),
('A0000019', 'AC000004', 'Hollup, Let Him Cook!', 'Focus for 1 hour', FALSE, FALSE),
('A0000020', 'AC000003', 'Hey, You Are Not Me!', 'Name yourself as the developer name', FALSE, FALSE);


INSERT INTO ITEM (ITEMID, ITEMNAME, ITEMTYPE, ITEMSTATUS, ITEMCOST, ISARCHIVED, ISDELETED, ITEMIMAGE) VALUES
('IT000001', 'Mystic Fox', 'Creature', TRUE, 5000, FALSE, FALSE, NULL),
('IT000002', 'Lunar Rabbit', 'Creature', TRUE, 4500, FALSE, FALSE, NULL),
('IT000003', 'Emerald Owl', 'Creature', TRUE, 4000, FALSE, FALSE, NULL),
('IT000004', 'Crystal Deer', 'Creature', TRUE, 6000, FALSE, FALSE, NULL),
('IT000005', 'Shadow Cat', 'Creature', TRUE, 5500, FALSE, FALSE, NULL),
('IT000006', 'Firefly Swarm', 'Effect', TRUE, 3000, FALSE, FALSE, NULL),
('IT000007', 'Floating Jellyfish', 'Effect', TRUE, 3500, FALSE, FALSE, NULL),
('IT000008', 'Floating Lilies', 'Effect', TRUE, 2500, FALSE, FALSE, NULL),
('IT000009', 'Firefly Lantern', 'Furniture', TRUE, 2800, FALSE, FALSE, NULL),
('IT000010', 'Mushroom Bench', 'Furniture', TRUE, 3200, FALSE, FALSE, NULL),
('IT000011', 'Crystal Fountain', 'Furniture', TRUE, 5000, FALSE, FALSE, NULL),
('IT000012', 'Sakura Swing', 'Furniture', TRUE, 4000, FALSE, FALSE, NULL),
('IT000013', 'Mystic Rock Circle', 'Furniture', TRUE, 4500, FALSE, FALSE, NULL),
('IT000014', 'Rainbow Waterfall', 'Scenery', TRUE, 7000, FALSE, FALSE, NULL),
('IT000015', 'Glowing Mushroom Patch', 'Scenery', TRUE, 3200, FALSE, FALSE, NULL),
('IT000016', 'Wind Chime Station', 'Furniture', TRUE, 3500, FALSE, FALSE, NULL),
('IT000017', 'Floating Lanterns', 'Effect', TRUE, 3000, FALSE, FALSE, NULL),
('IT000018', 'Whispering Pond', 'Scenery', TRUE, 4800, FALSE, FALSE, NULL),
('IT000019', 'Wishing Well', 'Scenery', TRUE, 5200, FALSE, FALSE, NULL),
('IT000020', 'BMW', 'Vehicle', TRUE, 15000, FALSE, FALSE, NULL),
('IT000021', 'Shovel', 'Tool', TRUE, 800, FALSE, FALSE, NULL);


-- TREEBOX Table
INSERT INTO TREEBOX (TREEBOXID, TREEBOXNAME, TREEBOXCOST, ISDELETED, TREEBOXIMAGE) VALUES
('TB000001', 'Common', 100, FALSE, NULL),
('TB000002', 'Rare', 300, FALSE, NULL),
('TB000003', 'Epic', 500, FALSE, NULL),
('TB000004', 'Legendary', 1500, FALSE, NULL);


-- TREERARITY Table
INSERT INTO TREERARITY (RARITYID, RARITYNAME, RARITYCOLOUR, ISARCHIVED, ISDELETED) VALUES
('TR000001', 'Common', '#A0A0A0', FALSE, FALSE),
('TR000002', 'Rare', '#0070DD', FALSE, FALSE),
('TR000003', 'Epic', '#A335EE', FALSE, FALSE),
('TR000004', 'Legendary', '#FF8000', FALSE, FALSE);


-- TREE Table
INSERT INTO TREE (TREEID, RARITYID, TREENAME, TREEDESCRIPTION, ISARCHIVED, ISDELETED, TREESTATUS, TREEIMAGE) VALUES
-- Common Trees (TR000001)
('T0000001', 'TR000001', 'Apple Tree', 'A tree that bears delicious apples.', FALSE, FALSE, TRUE, NULL),
('T0000002', 'TR000001', 'Cherry Blossom', 'A beautiful tree with pink blossoms.', FALSE, FALSE, TRUE, NULL),
('T0000003', 'TR000001', 'Coconut Palm', 'A tall tropical tree producing coconuts.', FALSE, FALSE, TRUE, NULL),
('T0000004', 'TR000001', 'Maple Tree', 'A tree with vibrant autumn foliage.', FALSE, FALSE, TRUE, NULL),
('T0000005', 'TR000001', 'Oak Tree', 'A sturdy and majestic tree.', FALSE, FALSE, TRUE, NULL),
('T0000006', 'TR000001', 'Pine Tree', 'An evergreen tree with needle-like leaves.', FALSE, FALSE, TRUE, NULL),
('T0000007', 'TR000001', 'Poplar Tree', 'A fast-growing tree with slender leaves.', FALSE, FALSE, TRUE, NULL),
('T0000008', 'TR000001', 'White Birch', 'A tree with distinctive white bark.', FALSE, FALSE, TRUE, NULL),
('T0000009', 'TR000001', 'Willow Tree', 'A tree with graceful, drooping branches.', FALSE, FALSE, TRUE, NULL),
-- Rare Trees (TR000003)
('T0000010', 'TR000002', 'Amethyst Sakura', 'A mystical tree with purple blossoms.', FALSE, FALSE, TRUE, NULL),
('T0000011', 'TR000002', 'Blue Mist Cypress', 'A cypress tree shrouded in blue mist.', FALSE, FALSE, TRUE, NULL),
('T0000012', 'TR000002', 'Flame Palm', 'A palm tree with fiery red leaves.', FALSE, FALSE, TRUE, NULL),
('T0000013', 'TR000002', 'Luminous Birch', 'A birch tree that emits a soft glow at night.', FALSE, FALSE, TRUE, NULL),
('T0000014', 'TR000002', 'Ruby Pomegranate', 'A tree bearing ruby-like pomegranates.', FALSE, FALSE, TRUE, NULL),
('T0000015', 'TR000002', 'Silver Olive', 'An olive tree with silvery leaves.', FALSE, FALSE, TRUE, NULL),
('T0000016', 'TR000002', 'Wisteria Orchid', 'A cascading tree with purple flowers.', FALSE, FALSE, TRUE, NULL),
-- Epic Trees (TR000003)
('T0000017', 'TR000003', 'Frostpine', 'An ancient pine tree with frost-covered branches.', FALSE, FALSE, TRUE, NULL),
('T0000018', 'TR000003', 'Jade Banyan', 'A banyan tree with jade-green roots.', FALSE, FALSE, TRUE, NULL),
('T0000019', 'TR000003', 'Moonlight Willow', 'A willow tree that glows under the moonlight.', FALSE, FALSE, TRUE, NULL),
('T0000020', 'TR000003', 'Phoenix Wutong', 'A legendary tree associated with the phoenix.', FALSE, FALSE, TRUE, NULL),
('T0000021', 'TR000003', 'Rainbow Eucalyptus', 'A tree with multicolored bark.', FALSE, FALSE, TRUE, NULL),
('T0000022', 'TR000003', 'Thunder Oak', 'A mighty oak tree crackling with energy.', FALSE, FALSE, TRUE, NULL),
-- Event-Limited Trees 
('T0000023', 'TR000004', 'Christmas Miracle', 'A festive tree that glows during the holidays.', FALSE, FALSE, TRUE, NULL),
('T0000024', 'TR000004', 'Frostbloom Plum', 'A winter tree that blossoms with icy petals.', FALSE, FALSE, TRUE, NULL),
('T0000025', 'TR000004', 'Halloween Shadow', 'A haunted tree with eerie glowing patterns.', FALSE, FALSE, TRUE, NULL),
('T0000026', 'TR000004', 'Netherwood Wraith', 'A spectral tree from the underworld.', FALSE, FALSE, TRUE, NULL),
('T0000027', 'TR000004', 'Sakura Dream', 'A legendary sakura tree seen in dreams.', FALSE, FALSE, TRUE, NULL),
-- Legendary Trees
('T0000028', 'TR000004', 'Stellar Ancient', 'A cosmic tree connected to the stars.', FALSE, FALSE, TRUE, NULL),
('T0000029', 'TR000004', 'Tree of Eternity', 'An eternal tree embodying wisdom and time.', FALSE, FALSE, TRUE, NULL);


-- QUESTION Table
INSERT INTO QUESTION (QUESTIONID, QUESTIONDESCRIPTION, ISDELETED, ISARCHIVED) VALUES
('Q0000001', 'How are you feeling today?', FALSE, FALSE),
('Q0000002', 'What is a goal you’re currently working toward?', FALSE, FALSE),
('Q0000003', 'What is one thing you are grateful for today?', FALSE, FALSE),
('Q0000004', 'If you could give your younger self one piece of advice, what would it be?', FALSE, FALSE),
('Q0000005', 'What does happiness mean to you?', FALSE, FALSE),
('Q0000006', 'How do you handle stress in your daily life?', FALSE, FALSE),
('Q0000007', 'What is something you’ve recently learned about yourself?', FALSE, FALSE),
('Q0000008', 'What is a challenge you’ve overcome, and how did it change you?', FALSE, FALSE),
('Q0000009', 'If you had unlimited courage, what would you do differently in your life?', FALSE, FALSE),
('Q0000010', 'What is one thing you can do today to improve your mental well-being?', FALSE, FALSE),
('Q0000011', 'Do you believe everything happens for a reason? Why or why not?', FALSE, FALSE),
('Q0000012', 'What role does forgiveness play in your life?', FALSE, FALSE),
('Q0000013', 'What is a past mistake that helped you grow as a person?', FALSE, FALSE),
('Q0000014', 'How do you define success in life?', FALSE, FALSE),
('Q0000015', 'What is one fear you would like to overcome?', FALSE, FALSE),
('Q0000016', 'Do you think people are inherently good or bad? Why?', FALSE, FALSE),
('Q0000017', 'What does self-love mean to you?', FALSE, FALSE),
('Q0000018', 'If you could change one thing about the world, what would it be?', FALSE, FALSE),
('Q0000019', 'What is a belief you once held strongly but have since changed?', FALSE, FALSE),
('Q0000020', 'How do you stay present and mindful in your daily life?', FALSE, FALSE),
('Q0000021', 'If you could spend a day in deep conversation with anyone, living or dead, who would it be and why?', FALSE, FALSE),
('Q0000022', 'What is the most valuable lesson you have learned from pain or suffering?', FALSE, FALSE),
('Q0000023', 'What is something that always brings you peace?', FALSE, FALSE),
('Q0000024', 'If you had to describe your life journey in one word, what would it be?', FALSE, FALSE),
('Q0000025', 'How do you usually handle failure, and how can you improve your response to it?', FALSE, FALSE),
('Q0000026', 'What does true freedom mean to you?', FALSE, FALSE),
('Q0000027', 'What is one habit that has positively impacted your life?', FALSE, FALSE),
('Q0000028', 'What would your ideal day look like, and how can you bring parts of it into reality?', FALSE, FALSE),
('Q0000029', 'Do you think people can truly change? Why or why not?', FALSE, FALSE),
('Q0000030', 'What is one boundary you need to set for your well-being?', FALSE, FALSE),
('Q0000031', 'What brings you a sense of purpose in life?', FALSE, FALSE),
('Q0000032', 'If your emotions could speak, what would they say right now?', FALSE, FALSE),
('Q0000033', 'What is one thing you would like to let go of?', FALSE, FALSE),
('Q0000034', 'What is a memory that always makes you smile?', FALSE, FALSE),
('Q0000035', 'Do you believe that suffering is necessary for growth? Why or why not?', FALSE, FALSE),
('Q0000036', 'How do you show kindness to yourself?', FALSE, FALSE),
('Q0000037', 'What is one thing you wish more people knew about you?', FALSE, FALSE),
('Q0000038', 'If you could master one new skill instantly, what would it be and why?', FALSE, FALSE),
('Q0000039', 'What do you value most in a friendship?', FALSE, FALSE),
('Q0000040', 'If your life were a book, what would the current chapter be titled?', FALSE, FALSE),
('Q0000041', 'What is one piece of advice you would give to someone struggling?', FALSE, FALSE),
('Q0000042', 'How do you want to be remembered?', FALSE, FALSE),
('Q0000043', 'What is one small act of kindness that had a big impact on you?', FALSE, FALSE),
('Q0000044', 'If you could relive one moment in your life, which one would it be and why?', FALSE, FALSE),
('Q0000045', 'What is something you are currently working on within yourself?', FALSE, FALSE),
('Q0000046', 'How do you handle uncertainty in life?', FALSE, FALSE),
('Q0000047', 'What is a belief or mindset that has helped you through difficult times?', FALSE, FALSE),
('Q0000048', 'If you could ask your future self one question, what would it be?', FALSE, FALSE),
('Q0000049', 'What is one thing that makes you feel truly alive?', FALSE, FALSE),
('Q0000050', 'How do you define personal growth?', FALSE, FALSE);


-- MUSIC Table
INSERT INTO MUSIC (MUSICID, MUSICNAME, AUTHOR, FILEPATH, MHOUR, MMINUTE, MSECOND, ISDELETED) VALUES
('M0000001', 'Whispering Winds', 'Aetheria Sounds', '/music/whispering_winds.mp3', 0, 3, 45, FALSE),
('M0000002', 'Lunar Serenity', 'Celestine Harmony', '/music/lunar_serenity.mp3', 0, 4, 12, FALSE),
('M0000003', 'Ethereal Dreamscape', 'Nova Echo', '/music/ethereal_dreamscape.mp3', 0, 5, 27, FALSE),
('M0000004', 'Twilight Reverie', 'Solstice Melodies', '/music/twilight_reverie.mp3', 0, 3, 59, FALSE),
('M0000005', 'Aurora Waltz', 'Nebula Symphony', '/music/aurora_waltz.mp3', 0, 6, 14, FALSE),
('M0000006', 'Starlit Echoes', 'Astralis Orchestra', '/music/starlit_echoes.mp3', 0, 2, 48, FALSE),
('M0000007', 'Celestial Harmony', 'Zenith Tones', '/music/celestial_harmony.mp3', 0, 4, 33, FALSE),
('M0000008', 'Mystic Rain', 'Evernight Ensemble', '/music/mystic_rain.mp3', 0, 3, 21, FALSE),
('M0000009', 'Forest Whispers', 'Verdant Muse', '/music/forest_whispers.mp3', 0, 5, 5, FALSE),
('M0000010', 'Eclipsing Shadows', 'Lunar Veil', '/music/eclipsing_shadows.mp3', 0, 4, 50, FALSE);


-- THERAPIST Table
--INSERT INTO THERAPIST (THERAPISTID, THERAPISTNAME, THERAPISTPW, THERAPISTEMAIL, THERAPISTSTATUS, ISDELETED, THERAPISTIMAGE) VALUES
--('TP000001', 'Dr. Aiman Zulkifli', 'P@ssw0rd123', 'aiman.zulkifli@therapymsia.com', TRUE, FALSE, NULL),
--('TP000002', 'Nurul Hidayah Tan', 'SecurePass456', 'nurul.hidayah@therapymsia.com', TRUE, FALSE, NULL),
--('TP000003', 'Hafiz Rahman', 'HafizStrong789', 'hafiz.rahman@therapymsia.com', TRUE, FALSE, NULL),
--('TP000004', 'Chong Wei Ling', 'TherapyMaster321', 'chong.weiling@therapymsia.com', TRUE, FALSE, NULL),
--('TP000005', 'Rajesh Kumar', 'HealingTouch999', 'rajesh.kumar@therapymsia.com', TRUE, FALSE, NULL),
--('TP000006', 'Farah Aisyah Ahmad', 'MindCare888', 'farah.aisyah@therapymsia.com', TRUE, FALSE, NULL),
--('TP000007', 'Daniel Yong', 'CalmMind777', 'daniel.yong@therapymsia.com', TRUE, FALSE, NULL),
--('TP000008', 'Siti Zainab Osman', 'TranquilSoul555', 'siti.zainab@therapymsia.com', TRUE, FALSE, NULL),
--('TP000009', 'Haris Iskandar', 'PeacefulMind234', 'haris.iskandar@therapymsia.com', TRUE, FALSE, NULL),
--('TP000010', 'Lim Mei Xin', 'TherapistPass678', 'lim.meixin@therapymsia.com', TRUE, FALSE, NULL);

INSERT INTO THERAPIST (THERAPISTID, THERAPISTNAME, THERAPISTPW, THERAPISTEMAIL, AREAOFINTEREST, LANGUAGESPOKEN, YEAROFEXP, CULTURE, THERAPISTSTATUS, ISDELETED, THERAPISTIMAGE) VALUES
('TP000001', 'Dr. Aiman Zulkifli', 'P@ssw0rd123', 'aiman.zulkifli@therapymsia.com', 'Depression and anxiety, Emotion regulation, Self-exploration, Self-worth and esteem, Work and study stress', 'English, Malay', 6, 'Muslim', TRUE, FALSE, NULL),
('TP000002', 'Nurul Hidayah Tan', 'SecurePass456', 'nurul.hidayah@therapymsia.com', 'Work and study stress, Relationship issues, Self-worth and esteem', 'English, Malay', 3, 'Muslim', TRUE, FALSE, NULL),
('TP000003', 'Hafiz Rahman', 'HafizStrong789', 'hafiz.rahman@therapymsia.com', 'Self-worth and esteem, Trauma and self-harm, Body image, Emotion regulation, Relationship issues', 'English, Malay', 5, 'Muslim', TRUE, FALSE, NULL),
('TP000004', 'Chong Wei Ling', 'TherapyMaster321', 'chong.weiling@therapymsia.com', 'Emotion regulation, Self-esteem, Relationship issues, Trauma and self-harm', 'English, Chinese', 12, 'Buddhist', TRUE, FALSE, NULL),
('TP000005', 'Rajesh Kumar', 'HealingTouch999', 'rajesh.kumar@therapymsia.com', 'Depression and anxiety, Work and study stress, Self-exploration, Self-esteem, Body image', 'English, Tamil', 7, 'Hindu', TRUE, FALSE, NULL),
('TP000006', 'Farah Aisyah Ahmad', 'MindCare888', 'farah.aisyah@therapymsia.com', 'Self-exploration, Work and study stress, Self-worth and esteem, Trauma and self-harm', 'English, Malay', 6, 'Muslim', TRUE, FALSE, NULL),
('TP000007', 'Daniel Yong', 'CalmMind777', 'daniel.yong@therapymsia.com', 'Trauma and self-harm, Relationship issues, Emotion regulation, Depression and anxiety, Self-esteem', 'English, Chinese', 9, 'Christian', TRUE, FALSE, NULL),
('TP000008', 'Siti Zainab Osman', 'TranquilSoul555', 'siti.zainab@therapymsia.com', 'Depression and anxiety, Self-worth and esteem, Body image, Work and study stress', 'English, Malay', 10, 'Muslim', TRUE, FALSE, NULL),
('TP000009', 'Haris Iskandar', 'PeacefulMind234', 'haris.iskandar@therapymsia.com', 'Work and study stress, Emotion regulation, Self-esteem, Relationship issues, Self-exploration', 'English, Malay', 5, 'Muslim', TRUE, FALSE, NULL),
('TP000010', 'Lim Mei Xin', 'TherapistPass678', 'lim.meixin@therapymsia.com', 'Self-esteem, Body image, Relationship issues, Trauma and self-harm, Emotion regulation', 'English, Chinese', 7, 'Buddhist', TRUE, FALSE, NULL);




-- TASK Table
INSERT INTO TASK (TASKID, TASKNAME, TASKDESCRIPTION, ISCUSTOMISABLE, ISARCHIVED, ISDELETED) VALUES
('TK000001', 'Morning Meditation', 'Spend 10 minutes meditating to start the day with clarity.', TRUE, FALSE, FALSE),
('TK000002', 'Water the Trees', 'Ensure all trees in your forest are well-watered.', FALSE, FALSE, FALSE),
('TK000003', 'Daily Walk', 'Take a 30-minute walk to refresh your mind and body.', TRUE, FALSE, FALSE),
('TK000004', 'Read a Chapter', 'Read one chapter of a book to expand your knowledge.', TRUE, FALSE, FALSE),
('TK000005', 'Write a Journal Entry', 'Reflect on your day and write about your experiences.', TRUE, FALSE, FALSE),
('TK000006', 'Complete a Focus Session', 'Spend 25 minutes working on a deep-focus task.', FALSE, FALSE, FALSE),
('TK000007', 'Stretching Routine', 'Do a 10-minute full-body stretching routine.', TRUE, FALSE, FALSE),
('TK000008', 'Check-In on Goals', 'Review your progress and adjust your daily goals.', FALSE, FALSE, FALSE),
('TK000009', 'Practice Gratitude', 'Write down three things you are grateful for.', TRUE, FALSE, FALSE),
('TK000010', 'Night Reflection', 'Take a moment to reflect on what went well today.', TRUE, FALSE, FALSE);


-- USERLEVEL Table
INSERT INTO USERLEVEL (LEVELID, REQUIREDXP, ISDELETED) VALUES
(0, 0, FALSE),
(1, 100, FALSE),
(2, 200, FALSE),
(3, 400, FALSE),
(4, 700, FALSE),
(5, 1100, FALSE),
(6, 1600, FALSE),
(7, 2200, FALSE),
(8, 3000, FALSE),
(9, 4000, FALSE),
(10, 5200, FALSE),
(11, 6700, FALSE),
(12, 8600, FALSE),
(13, 11000, FALSE),
(14, 14000, FALSE),
(15, 18000, FALSE),
(16, 23000, FALSE),
(17, 30000, FALSE),
(18, 38000, FALSE),
(19, 48000, FALSE),
(20, 60000, FALSE),
(21, 75000, FALSE),
(22, 93000, FALSE),
(23, 115000, FALSE),
(24, 140000, FALSE),
(25, 170000, FALSE),
(26, 205000, FALSE),
(27, 245000, FALSE),
(28, 295000, FALSE),
(29, 355000, FALSE),
(30, 430000, FALSE);


-- LEVELREWARD Table
INSERT INTO LEVELREWARD (LEVELREWARDID, LEVELID, TREEBOXID, ITEMID, QUANTITY, ISDELETED) VALUES
('LR000001', 5, 'TB000001', 'IT000005', 1, FALSE),  
('LR000002', 10, 'TB000002', 'IT000010', 3, FALSE), 
('LR000003', 15, 'TB000003', 'IT000015', 4, FALSE),
('LR000004', 20, 'TB000004', 'IT000020', 5, FALSE);


-- RARITYDROPRATE Table
INSERT INTO RARITYDROPRATE (TREEBOXID, RARITYID, PERCENTAGE, ISDELETED) VALUES
('TB000001', 'TR000001', 70.00, FALSE),
('TB000001', 'TR000002', 20.00, FALSE),
('TB000001', 'TR000003', 8.00, FALSE),
('TB000001', 'TR000004', 2.00, FALSE);

-- USERTASKLIST Table
INSERT INTO USERTASKLIST (USERTASKLISTID, TASKID, USERID, CUSTOMISEDTASKNAME, CUSTOMISEDTASKDESCRIPTION, DATETIMEACCEPTED, DATECOMPLETED, ISDELETED) VALUES
('UTL00001', 'TK000001', 'U2500005', 'Morning Meditation', 'Spend 10 minutes meditating to start the day with clarity.', '2025-03-10 08:00:00', NULL, FALSE),
('UTL00002', 'TK000002', 'U2500005', 'Water the Trees', 'Ensure all trees in your forest are well-watered.', '2025-03-10 09:00:00', NULL, FALSE),
('UTL00003', 'TK000003', 'U2500005', 'Daily Walk', 'Take a 30-minute walk to refresh your mind and body.', '2025-03-10 10:00:00', NULL, FALSE),
('UTL00004', 'TK000004', 'U2500005', 'Read a Chapter', 'Read one chapter of a book to expand your knowledge.', '2025-03-10 11:00:00', NULL, FALSE),
('UTL00005', 'TK000005', 'U2500005', 'Write a Journal Entry', 'Reflect on your day and write about your experiences.', '2025-03-10 12:00:00', NULL, FALSE),
('UTL00006', 'TK000006', 'U2500005', 'Complete a Focus Session', 'Spend 25 minutes working on a deep-focus task.', '2025-03-10 13:00:00', NULL, FALSE),
('UTL00007', 'TK000007', 'U2500005', 'Stretching Routine', 'Do a 10-minute full-body stretching routine.', '2025-03-10 14:00:00', NULL, FALSE),
('UTL00008', 'TK000008', 'U2500005', 'Check-In on Goals', 'Review your progress and adjust your daily goals.', '2025-03-10 15:00:00', NULL, FALSE),
('UTL00009', 'TK000009', 'U2500005', 'Practice Gratitude', 'Write down three things you are grateful for.', '2025-03-10 16:00:00', NULL, FALSE),
('UTL00010', 'TK000010', 'U2500005', 'Night Reflection', 'Take a moment to reflect on what went well today.', '2025-03-10 18:00:00', NULL, FALSE);


-- BIOME Table
INSERT INTO BIOME (BIOMEID, BIOMENAME, BIOMEDESCRIPTION, BIOMECOST, ISDELETED, ISARCHIVED, BIOMEIMAGE) VALUES 
('B0000001', 'Forest', 'A lush, green biome filled with trees, plants, and wildlife.', 500, FALSE, FALSE, NULL),
('B0000002', 'Desert', 'A vast and dry biome with sand dunes, cacti, and little rainfall.', 300, FALSE, FALSE, NULL),
('B0000003', 'Ocean', 'A large water biome with vast seas, coral reefs, and marine life.', 800, FALSE, FALSE, NULL),
('B0000004', 'Mountain', 'A rugged biome with high altitudes, steep cliffs, and snow-covered peaks.', 1000, FALSE, FALSE, NULL),
('B0000005', 'Swamp', 'A damp biome with murky waters, dense vegetation, and swamp creatures.', 400, FALSE, FALSE, NULL);


-- LAND Table
INSERT INTO LAND (LANDID, USERID, BIOMEID, LANDNAME, ISMAINLAND, ISDELETED) VALUES 
('L0000001', 'U2500001', 'B0000001', 'Andrew Forest', TRUE, FALSE),
('L0000002', 'U2500002', 'B0000002', 'Huai Ern Desert', TRUE, FALSE),
('L0000003', 'U2500003', 'B0000003', 'Jian Hui Ocean', TRUE, FALSE),
('L0000004', 'U2500004', 'B0000004', 'Choon Chong Mountain', TRUE, FALSE),
('L0000005', 'U2500005', 'B0000005', 'Jia Quan Swamp', TRUE, FALSE),
('L0000006', 'U2500006', 'B0000001', 'Robert Forest Retreat', FALSE, FALSE),
('L0000007', 'U2500007', 'B0000002', 'Nancy Desert Oasis', FALSE, FALSE),
('L0000008', 'U2500008', 'B0000003', 'Leah Ocean Paradise', FALSE, FALSE),
('L0000009', 'U2500009', 'B0000004', 'Jason Mountain Base', FALSE, FALSE),
('L0000010', 'U2500010', 'B0000005', 'Sandra Swamp Haven', FALSE, FALSE);


-- LANDCONTENT Table (Currently none)
INSERT INTO LANDCONTENT (LANDCONTENTID, LANDID, ITEMID, TREEID, XCOORD, YCOORD, ISDELETED) VALUES 
('LCAA0001', 'L0000001', 'IT000001', NULL, 10, 20, FALSE),
('LCAA0002', 'L0000001', NULL, 'T0000001', 30, 40, FALSE);


-- RESPONE Table
INSERT INTO RESPONSE (RESPONSEID, USERID, QUESTIONID, RESPONSEDESCRIPTION, ISARCHIVED, ISDELETED) VALUES
('R0000001', 'U2500005', 'Q0000001', 'I am grateful for my family and their constant support.', FALSE, FALSE),
('R0000002', 'U2500005', 'Q0000002', 'I would tell my younger self to trust the journey and be patient.', FALSE, FALSE),
('R0000003', 'U2500005', 'Q0000003', 'Happiness is the ability to appreciate the small moments.', FALSE, FALSE),
('R0000004', 'U2500005', 'Q0000004', 'I handle stress by taking deep breaths and focusing on what I can control.', FALSE, FALSE),
('R0000005', 'U2500005', 'Q0000005', 'I recently learned that I am stronger than I thought.', FALSE, FALSE),
('R0000006', 'U2500005', 'Q0000006', 'Overcoming self-doubt has made me more confident in my decisions.', FALSE, FALSE),
('R0000007', 'U2500005', 'Q0000007', 'I would take more risks and not let fear hold me back.', FALSE, FALSE),
('R0000008', 'U2500005', 'Q0000008', 'Today, I will take a break and spend time in nature.', FALSE, FALSE),
('R0000009', 'U2500005', 'Q0000009', 'Yes, I believe everything happens for a reason because it helps me grow.', FALSE, FALSE),
('R0000010', 'U2500005', 'Q0000010', 'Forgiveness is freeing myself from past burdens.', FALSE, FALSE);


-- USERACHIEVEMENT Table
INSERT INTO USERACHIEVEMENT (USERID, ACHIEVEMENTID, DATECOMPLETED, ISDELETED) VALUES
('U2500001', 'A0000001', '2025-03-01', FALSE),
('U2500002', 'A0000002', '2025-03-02', FALSE),
('U2500003', 'A0000003', '2025-03-03', FALSE),
('U2500004', 'A0000004', '2025-03-04', FALSE),
('U2500005', 'A0000005', '2025-03-05', FALSE),
('U2500006', 'A0000001', '2025-03-06', FALSE),
('U2500007', 'A0000002', '2025-03-07', FALSE),
('U2500008', 'A0000003', '2025-03-08', FALSE),
('U2500009', 'A0000004', '2025-03-09', FALSE),
('U2500010', 'A0000005', '2025-03-10', FALSE);


-- USERINVENTORYITEM Table
INSERT INTO USERINVENTORYITEM (INVENTORYITEMID, USERID, TREEBOXID, ITEMID, TREEID, BIOMEID, QUANTITY, ISDELETED) VALUES
('IVAA0001', 'U2500001', 'TB000001', NULL, NULL, NULL, 3, FALSE),
('IVAA0002', 'U2500001', NULL, 'IT000001', NULL, NULL, 5, FALSE),
('IVAA0003', 'U2500001', 'TB000002', NULL, NULL, NULL, 2, FALSE),
('IVAA0004', 'U2500001', NULL, 'IT000002', NULL, NULL, 1, FALSE),
('IVAA0005', 'U2500001', 'TB000003', NULL, NULL, NULL, 4, FALSE),
('IVAA0006', 'U2500001', NULL, 'IT000003', NULL, NULL, 2, FALSE),
('IVAA0007', 'U2500002', 'TB000004', NULL, NULL, NULL, 1, FALSE),
('IVAA0008', 'U2500002', NULL, 'IT000004', NULL, NULL, 3, FALSE),
('IVAA0009', 'U2500002', 'TB000004', NULL, NULL, NULL, 2, FALSE),
('IVAA0010', 'U2500001', NULL, NULL, NULL, 'B0000001', 1, FALSE),
('IVAA0011', 'U2500002', NULL, NULL, NULL, 'B0000001', 1, FALSE),
('IVAA0012', 'U2500001', NULL, NULL, NULL, 'B0000002', 1, FALSE);

-- ACHIEVEMENTREWARD Table
INSERT INTO ACHIEVEMENTREWARD (ACHIEVEMENTREWARDID, ACHIEVEMENTID, TREEBOXID, ITEMID, QUANTITY, ISDELETED) VALUES
('AR000001', 'A0000001', 'TB000001', NULL, 2, FALSE),
('AR000002', 'A0000001', NULL, 'IT000001', 5, FALSE),
('AR000003', 'A0000002', 'TB000002', NULL, 3, FALSE),
('AR000004', 'A0000002', NULL, 'IT000002', 2, FALSE),
('AR000005', 'A0000003', 'TB000003', NULL, 1, FALSE),
('AR000006', 'A0000003', NULL, 'IT000003', 4, FALSE),
('AR000007', 'A0000004', 'TB000004', NULL, 2, FALSE),
('AR000008', 'A0000004', NULL, 'IT000004', 3, FALSE),
('AR000009', 'A0000005', 'TB000004', NULL, 1, FALSE),
('AR000010', 'A0000005', NULL, 'IT000005', 6, FALSE);


-- USERBADGE Table
INSERT INTO USERBADGE (USERID, BADGEID, ISSELECTED, ISDELETED) VALUES
('U2500001', 'BA000001', TRUE, FALSE),
('U2500002', 'BA000002', FALSE, FALSE),
('U2500003', 'BA000003', FALSE, FALSE),
('U2500004', 'BA000004', TRUE, FALSE),
('U2500005', 'BA000001', FALSE, FALSE),
('U2500006', 'BA000003', FALSE, FALSE),
('U2500007', 'BA000001', TRUE, FALSE),
('U2500008', 'BA000003', FALSE, FALSE),
('U2500009', 'BA000003', TRUE, FALSE),
('U2500010', 'BA000004', FALSE, FALSE);


-- USERFAVOURITE Table
INSERT INTO USERPLAYLIST (PLAYLISTID, USERID, PLAYLISTNAME, DATECREATED, ISDELETED) VALUES 
('PL000001', 'U2500001', 'Relaxing Tunes', '2025-03-10', FALSE);


-- USERPLAYLIST Table
INSERT INTO USERFAVOURITE (USERID, MUSICID, ISDELETED) VALUES 
('U2500001', 'M0000001', FALSE),
('U2500001', 'M0000003', FALSE),
('U2500001', 'M0000005', FALSE);


-- PLAYLISTMUSIC Table
INSERT INTO PLAYLISTMUSIC (PLAYLISTID, MUSICID, ISDELETED) VALUES 
('PL000001', 'M0000002', FALSE),
('PL000001', 'M0000004', FALSE),
('PL000001', 'M0000006', FALSE),
('PL000001', 'M0000008', FALSE);


-- FOCUSSESSION Table
INSERT INTO FOCUSSESSION (SESSIONID, USERID, SESSIONTYPE, DURATION, POMODOROMINORBREAK, POMODOROMAJORBREAK, SESSIONSTATUS, TREEBOXESOBTAINED, ISDELETED) VALUES
('SAA00001', 'U2500001', 'Pomodoro', 25.00, 5, 15, 'Completed', 1, FALSE),
('SAA00002', 'U2500002', 'Deep Work', 60.00, NULL, NULL, 'Completed', 2, FALSE),
('SAA00003', 'U2500003', 'Pomodoro', 50.00, 5, 15, 'Completed', 1, FALSE),
('SAA00004', 'U2500004', 'Custom', 75.00, NULL, NULL, 'In Progress', 0, FALSE),
('SAA00005', 'U2500005', 'Pomodoro', 25.00, 5, 15, 'Completed', 1, FALSE),
('SAA00006', 'U2500006', 'Deep Work', 90.00, NULL, NULL, 'Completed', 3, FALSE),
('SAA00007', 'U2500007', 'Custom', 45.00, NULL, NULL, 'Completed', 2, FALSE),
('SAA00008', 'U2500008', 'Pomodoro', 30.00, 5, 15, 'Completed', 1, FALSE),
('SAA00009', 'U2500009', 'Deep Work', 120.00, NULL, NULL, 'Completed', 4, FALSE),
('SAA00010', 'U2500010', 'Custom', 60.00, NULL, NULL, 'Completed', 2, FALSE);


-- DIARYENTRY Table
INSERT INTO DIARYENTRY (DIARYID, USERID, DIARYTITLE, DESCRIPTION, MOOD, DATEWRITTEN, ISARCHIVED, ISDELETED) VALUES
('D0000001', 'U2500005', 'A Fresh Start', 'Today felt like a new beginning. I finally organized my workspace and planned my goals for the month. Feeling optimistic!', 'Happy', '2025-03-07', FALSE, FALSE),
('D0000002', 'U2500005', 'Unexpected Challenges', 'Work was hectic today. A last-minute bug surfaced, and debugging took longer than expected. Hoping tomorrow will be smoother.', 'Frustrated', '2025-03-08', TRUE, FALSE),
('D0000003', 'U2500005', 'Quiet Reflections', 'Spent the evening reading and reflecting. Sometimes, silence is the best companion.', 'Calm', '2025-03-09', FALSE, TRUE),
('D0000004', 'U2500005', 'Weekend Plans', 'Excited for the weekend! Thinking of visiting a new café and catching up on some personal projects.', 'Excited', '2025-03-10', FALSE, FALSE);


-- TAG Table
INSERT INTO TAG (TAGID, TAGNAME, ISCUSTOMISABLE, ISDELETED) VALUES
('TAG00001', 'Reflection', TRUE, FALSE),
('TAG00002', 'Work', TRUE, FALSE),
('TAG00003', 'Weekend', TRUE, FALSE);


-- USERTAG Table
INSERT INTO USERTAG (USERTAGID, USERID, TAGID, NEWTAGNAME) VALUES
('UT000001', 'U2500005', 'TAG00001', 'Self-reflection'),
('UT000002', 'U2500005', 'TAG00002', 'Work Challenges'),
('UT000003', 'U2500005', 'TAG00003', 'Personal Time');


-- DIARYTAG Table
INSERT INTO DIARYTAG (DIARYTAGID, DIARYID, USERTAGID, TAGID) VALUES
('DT000001', 'D0000001', 'UT000001', NULL),
('DT000002', 'D0000001', NULL, 'TAG00002'),
('DT000003', 'D0000002', 'UT000002', NULL),
('DT000004', 'D0000002', NULL, 'TAG00002'),
('DT000005', 'D0000003', 'UT000001', NULL),
('DT000006', 'D0000003', NULL, 'TAG00001'),
('DT000007', 'D0000004', 'UT000003', NULL),
('DT000008', 'D0000004', NULL, 'TAG00003');



-- THREADCATEGORY Table
INSERT INTO THREADCATEGORY (THREADCATEGORYID, THREADCATEGORYNAME, ISDELETED) VALUES
('THC00001', 'General Discussion', FALSE),
('THC00002', 'Productivity Tips', FALSE),
('THC00003', 'Feature Requests', FALSE),
('THC00004', 'Bug Reports', FALSE),
('THC00005', 'Mental Wellness', FALSE);


-- THREAD Table
INSERT INTO THREAD (THREADID, USERID, THREADCATEGORYID, THREADTITLE, THREADDESCRIPTION, UPVOTE, DOWNVOTE, SHARECOUNT, ISDELETED) VALUES
('TH000001', 'U2500001', 'THC00001', 'How do you stay productive?', 'Share your best productivity hacks!', 10, 0, 5, FALSE),
('TH000002', 'U2500002', 'THC00002', 'Best Pomodoro Timer Apps?', 'Looking for recommendations for Pomodoro apps!', 15, 1, 8, FALSE),
('TH000003', 'U2500003', 'THC00003', 'New Feature: Custom Tree Designs?', 'Would love to have custom tree options in the game!', 25, 3, 12, FALSE),
('TH000004', 'U2500004', 'THC00004', 'Game Crashes on Start', 'Has anyone experienced crashes when launching Mizuki’s Forest?', 5, 10, 3, FALSE),
('TH000005', 'U2500005', 'THC00005', 'Daily Gratitude Thread', 'Share one thing you are grateful for today!', 30, 0, 20, FALSE);


-- THREADIMAGE Table
INSERT INTO THREADIMAGE (IMAGEID, THREADID, ISMAINIMAGE, ISDELETED, IMAGE) VALUES
('IA000001', 'TH000001', TRUE, FALSE, NULL),
('IA000002', 'TH000002', TRUE, FALSE, NULL),
('IA000003', 'TH000003', TRUE, FALSE, NULL),
('IA000004', 'TH000004', TRUE, FALSE, NULL),
('IA000005', 'TH000005', TRUE, FALSE, NULL);


-- THREADCOMMENT Table
INSERT INTO THREADCOMMENT (THREADCOMMENTID, THREADID, USERID, COMMENTIDREPLYINGTO, CONTENT, UPVOTE, DOWNVOTE, ISDELETED) VALUES
('C0000001', 'TH000001', 'U2500002', NULL, 'I use the Eisenhower Matrix for prioritizing my tasks!', 7, 0, FALSE),
('C0000002', 'TH000002', 'U2500003', NULL, 'I recommend Forest and Focus To-Do, they work great!', 12, 1, FALSE),
('C0000003', 'TH000003', 'U2500004', NULL, 'Custom trees would be amazing! Maybe unlockable ones?', 18, 2, FALSE),
('C0000004', 'TH000004', 'U2500005', NULL, 'I had the same issue, reinstalling the game fixed it for me.', 4, 5, FALSE),
('C0000005', 'TH000005', 'U2500001', NULL, 'I am grateful for the sunny weather today!', 10, 0, FALSE);


-- THREADVOTE Table
INSERT INTO THREADVOTE (THREADID, USERID, VOTETYPE) VALUES
('TH000001', 'U2500001', TRUE),
('TH000002', 'U2500002', FALSE),
('TH000003', 'U2500003', TRUE),
('TH000004', 'U2500004', FALSE),
('TH000005', 'U2500005', TRUE);


-- COMMENTVOTE Table
INSERT INTO COMMENTVOTE (THREADCOMMENTID, USERID, VOTETYPE) VALUES
('C0000001', 'U2500001', TRUE),
('C0000002', 'U2500002', FALSE),
('C0000003', 'U2500003', TRUE),
('C0000004', 'U2500004', FALSE),
('C0000005', 'U2500005', TRUE);


-- USERTHERAPIST Table
INSERT INTO USERTHERAPIST (USERTHERAPISTID, USERID, THERAPISTID, DATEESTABLISHED, DATEEND, ISDELETED) VALUES
('UT000001', 'U2500001', 'TP000001', '2025-03-01', NULL, FALSE),
('UT000002', 'U2500002', 'TP000002', '2025-02-15', '2025-03-15', FALSE),
('UT000003', 'U2500003', 'TP000003', '2025-01-10', NULL, FALSE),
('UT000004', 'U2500004', 'TP000004', '2025-03-05', NULL, FALSE),
('UT000005', 'U2500005', 'TP000005', '2025-02-01', NULL, FALSE),
('UT000006', 'U2500006', 'TP000006', '2025-03-08', NULL, FALSE),
('UT000007', 'U2500007', 'TP000007', '2025-02-20', '2025-03-20', FALSE),
('UT000008', 'U2500008', 'TP000008', '2025-01-25', NULL, FALSE),
('UT000009', 'U2500009', 'TP000009', '2025-03-02', NULL, FALSE),
('UT000010', 'U2500010', 'TP000010', '2025-02-28', NULL, FALSE);


-- MESSAGE Table
INSERT INTO MESSAGE (MESSAGEID, USERID, THERAPISTID, SENDER, CONTENT, TIMESTAMPSENT, TIMESTAMPREAD, ISDELETED) VALUES
('MAA00001', 'U2500001', 'TP000001', 'USER', 'Hello Dr. Aiman, I need some help with managing stress.', '2025-03-01 08:00:00', NULL, FALSE),
('MAA00002', 'U2500002', 'TP000002', 'USER', 'Hi Nurul, could we discuss some anxiety issues I am facing?', '2025-03-02 09:15:00', '2025-03-02 09:30:00', FALSE),
('MAA00003', 'U2500003', 'TP000003', 'USER', 'Hey Hafiz, I’ve been struggling with time management, can we talk?', '2025-03-03 10:45:00', NULL, FALSE),
('MAA00004', 'U2500004', 'TP000004', 'USER', 'Hello Chong, I wanted to discuss thoughts on work-life balance.', '2025-03-05 11:00:00', NULL, FALSE),
('MAA00005', 'U2500005', 'TP000005', 'USER', 'Hi Rajesh, can you help me with my self-esteem issues?', '2025-03-06 12:30:00', NULL, FALSE),
('MAA00006', 'U2500006', 'TP000006', 'USER', 'Hi Farah, I need guidance on overcoming my social anxiety.', '2025-03-07 14:00:00', NULL, FALSE),
('MAA00007', 'U2500007', 'TP000007', 'USER', 'Hey Daniel, I’d like to talk about some personal struggles I’m facing.', '2025-03-08 15:30:00', NULL, FALSE),
('MAA00008', 'U2500008', 'TP000008', 'USER', 'Hello Siti, I need some help managing my emotions.', '2025-03-09 16:00:00', NULL, FALSE),
('MAA00009', 'U2500009', 'TP000009', 'USER', 'Hi Haris, can we talk about coping with loss and grief?', '2025-03-10 17:30:00', NULL, FALSE),
('MAA00010', 'U2500010', 'TP000010', 'USER', 'Hello Lim, I need advice on handling family issues.', '2025-03-11 18:00:00', NULL, FALSE);


-- TIMESLOT Table
INSERT INTO TIMESLOT (TIMESLOTID, THERAPISTID, TSDATE, STARTTIME, ENDTIME, STATUS, ISDELETED) VALUES
('TSA00001', 'TP000001', '2025-03-01', '09:00:00', '10:00:00', TRUE, FALSE),
('TSA00002', 'TP000002', '2025-03-02', '10:00:00', '11:00:00', TRUE, FALSE),
('TSA00003', 'TP000003', '2025-03-03', '11:00:00', '12:00:00', TRUE, FALSE),
('TSA00004', 'TP000004', '2025-03-04', '12:00:00', '13:00:00', TRUE, FALSE),
('TSA00005', 'TP000005', '2025-03-05', '14:00:00', '15:00:00', TRUE, FALSE),
('TSA00006', 'TP000006', '2025-03-06', '15:00:00', '16:00:00', TRUE, FALSE),
('TSA00007', 'TP000007', '2025-03-07', '16:00:00', '17:00:00', TRUE, FALSE),
('TSA00008', 'TP000008', '2025-03-08', '17:00:00', '18:00:00', TRUE, FALSE),
('TSA00009', 'TP000009', '2025-03-09', '18:00:00', '19:00:00', TRUE, FALSE),
('TSA00010', 'TP000010', '2025-03-10', '09:00:00', '10:00:00', TRUE, FALSE);


-- APPOINTMENT Table
INSERT INTO APPOINTMENT (APPOINTMENTID, USERID, TIMESLOTID, APPOINTMENTLINK, STATUS, ISDELETED) VALUES
('APAA0001', 'U2500001', 'TSA00001', 'www.therapymsia.com/appointment/APA0001', 'Scheduled', FALSE),
('APAA0002', 'U2500002', 'TSA00002', 'www.therapymsia.com/appointment/APA0002', 'Scheduled', FALSE),
('APAA0003', 'U2500003', 'TSA00003', 'www.therapymsia.com/appointment/APA0003', 'Scheduled', FALSE),
('APAA0004', 'U2500004', 'TSA00004', 'www.therapymsia.com/appointment/APA0004', 'Scheduled', FALSE),
('APAA0005', 'U2500005', 'TSA00005', 'www.therapymsia.com/appointment/APA0005', 'Scheduled', FALSE),
('APAA0006', 'U2500006', 'TSA00006', 'www.therapymsia.com/appointment/APA0006', 'Scheduled', FALSE),
('APAA0007', 'U2500007', 'TSA00007', 'www.therapymsia.com/appointment/APA0007', 'Scheduled', FALSE),
('APAA0008', 'U2500008', 'TSA00008', 'www.therapymsia.com/appointment/APA0008', 'Scheduled', FALSE),
('APAA0009', 'U2500009', 'TSA00009', 'www.therapymsia.com/appointment/APA0009', 'Scheduled', FALSE),
('APAA0010', 'U2500010', 'TSA00010', 'www.therapymsia.com/appointment/APA0010', 'Scheduled', FALSE);


-- CALLSESSION Table
INSERT INTO CALLSESSION (CALLSESSIONID, APPOINTMENTID, DURATION, ISDELETED) VALUES
('CSA00001', 'APAA0001', 30, FALSE),
('CSA00002', 'APAA0002', 45, FALSE),
('CSA00003', 'APAA0003', 50, FALSE),
('CSA00004', 'APAA0004', 60, FALSE),
('CSA00005', 'APAA0005', 40, FALSE),
('CSA00006', 'APAA0006', 35, FALSE),
('CSA00007', 'APAA0007', 55, FALSE),
('CSA00008', 'APAA0008', 50, FALSE),
('CSA00009', 'APAA0009', 60, FALSE),
('CSA00010', 'APAA0010', 45, FALSE);


-- TOKEN Table
INSERT INTO TOKEN (TOKENID, EXPIRE, USERID) VALUES
('00000001', CURRENT_TIMESTAMP, 'U2500001');








