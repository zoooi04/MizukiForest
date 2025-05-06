-- USERS Table, andrew update: only first 3 record is real life email value...
-- USERS Table, andrew update: only first 3 record is real life email value...
INSERT INTO USERS (USERID, USERNAME, USERPW, USEREMAIL, USERBIRTHDAY, COINS, EXP, USERLEVEL, LOGINSTREAK, DIARYVISIBILITY, FORESTVISIBILITY, ISDELETED, USERIMAGE) VALUES
('U2500001', 'Andrew Pheng', 'Andrew@123', 'andrewwwpqj@gmail.com', '1980-12-24', 936, 688, 3, 1, TRUE, TRUE, FALSE, NULL),
('U2500002', 'Huai Ern', 'Huaiern@123', '007kidlolipop@gmail.com', '2002-09-28', 880, 772, 4, 1, TRUE, TRUE, FALSE, NULL),
('U2500003', 'Jian Hui', 'jianhui@123', 'andrewpqj-pm22@student.tarc.edu.my', '1971-05-17', 507, 130, 1, 1, TRUE, TRUE, FALSE, NULL),
('U2500004', 'Choon Chong', 'cccc@123', 'mccallamy@hotmail.com', '1987-05-28', 735, 336, 2, 1, TRUE, TRUE, FALSE, NULL),
('U2500005', 'Jia Quan', '123qwe', 'leejiaquan721@gmail.com', '1980-05-12', 9999, 1111, 5, 1, TRUE, TRUE, FALSE, NULL),
('U2500006', 'robert32', 'vqj)C3Fx', 'quinncynthia@rivera.com', '1966-04-02', 998, 456, 3, 1, FALSE, TRUE, TRUE, NULL),
('U2500007', 'nthomas', '@0iAhIqC', 'nancycarroll@yahoo.com', '1989-11-10', 707, 276, 2, 1, FALSE, TRUE, FALSE, NULL),
('U2500008', 'rhodeskelsey', ')5%kRvMQ', 'leahpollard@hotmail.com', '1991-04-20', 673, 538, 3, 1, TRUE, TRUE, FALSE, NULL),
('U2500009', 'ann55', 'rc^f9GzL', 'josejohnson@yahoo.com', '1998-03-22', 651, 613, 3, 1, TRUE, TRUE, FALSE, NULL),
('U2500010', 'sandra66', 'o@e3JizS', 'millerjason@hotmail.com', '2001-03-06', 722, 293, 3, 1, TRUE, FALSE, TRUE, NULL);



-- BADGE Table
INSERT INTO BADGE (BADGEID, BADGENAME, BADGEIMAGE) VALUES
('BA000001','Tree Badge','/media/images/badge/Tree Badge.png'),
('BA000002','Journal Badge','/media/images/badge/Journal Badge.png'),
('BA000003','Music Badge','/media/images/badge/Music Badge.png'),
('BA000004','Therapy Badge','/media/images/badge/Therapy Badge.png'),
('BA000005','Forum Badge','/media/images/badge/Forum Badge.png');



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
('A0000011', 'AC000003', 'I have Already Met My Mona Li-Tree', 'Get the first legendary tree', FALSE, FALSE),
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
('IT000001', 'Mystic Fox', 'Creature', TRUE, 5000, FALSE, FALSE, '/media/images/item/mythic_fox.png'),
('IT000002', 'Lunar Rabbit', 'Creature', TRUE, 4500, FALSE, FALSE, '/media/images/item/lunar_rabbit.png'),
('IT000003', 'Emerald Owl', 'Creature', TRUE, 4000, FALSE, FALSE, '/media/images/item/emerald_owl.png'),
('IT000004', 'Crystal Deer', 'Creature', TRUE, 6000, FALSE, FALSE, '/media/images/item/crystal_deer.png'),
('IT000005', 'Shadow Cat', 'Creature', TRUE, 5500, FALSE, FALSE, '/media/images/item/shadow_cat.png'),
('IT000006', 'Firefly Swarm', 'Effect', TRUE, 3000, FALSE, FALSE, '/media/images/item/firefly_swarm.png'),
('IT000007', 'Floating Jellyfish', 'Effect', TRUE, 3500, FALSE, FALSE, '/media/images/item/floating_jellyfish.png'),
('IT000008', 'Floating Lilies', 'Effect', TRUE, 2500, FALSE, FALSE, '/media/images/item/floating_lilies.png'),
('IT000009', 'Firefly Lantern', 'Furniture', TRUE, 2800, FALSE, FALSE, '/media/images/item/firefly_lantern.png'),
('IT000010', 'Mushroom Bench', 'Furniture', TRUE, 3200, FALSE, FALSE, '/media/images/item/mushroom_bench.png'),
('IT000011', 'Crystal Fountain', 'Furniture', TRUE, 5000, FALSE, FALSE, '/media/images/item/crystal_fountain.png'),
('IT000012', 'Sakura Swing', 'Furniture', TRUE, 4000, TRUE, FALSE, '/media/images/item/sakura_swing.png'),
('IT000013', 'Mystic Rock Circle', 'Furniture', TRUE, 4500, FALSE, FALSE, '/media/images/item/mystic_rock_circle.png'),
('IT000014', 'Rainbow Waterfall', 'Scenery', TRUE, 7000, FALSE, FALSE, '/media/images/item/rainbow_waterfall.png'),
('IT000015', 'Glowing Mushroom Patch', 'Scenery', TRUE, 3200, FALSE, FALSE, '/media/images/item/glowing_mushroom_patch.png'),
('IT000016', 'Wind Chime Station', 'Furniture', TRUE, 3500, FALSE, FALSE, '/media/images/item/wind_chime_station.png'),
('IT000017', 'Floating Lanterns', 'Effect', TRUE, 3000, FALSE, FALSE, '/media/images/item/floating_lanterns.png'),
('IT000018', 'Whispering Pond', 'Scenery', TRUE, 4800, FALSE, FALSE, '/media/images/item/whispering_pond.png'),
('IT000019', 'Wishing Well', 'Scenery', TRUE, 5200, FALSE, FALSE, '/media/images/item/wishing_well.png'),
('IT000020', 'BMW', 'Vehicle', TRUE, 15000, FALSE, FALSE, '/media/images/item/bmw.png'),
('IT000021', 'Shovel', 'Tool', TRUE, 800, FALSE, FALSE, '/media/images/item/shovel.png'),

('IT000022','Phoebe Cube','Wuwa',TRUE,10000,FALSE,FALSE,'/media/images/item/Phoebe Cube.gif'),
('IT000023','Cube Party','Wuwa',TRUE,10000,FALSE,FALSE,'/media/images/item/Cube Party.gif'),
('IT000024','Camelya Cube Remastered','Wuwa',TRUE,10000,FALSE,FALSE,'/media/images/item/Camelya Cube Remastered.gif'),
('IT000025','Shopkeeper Cube','Wuwa',TRUE,10000,FALSE,FALSE,'/media/images/item/Shopkeeper Cube.gif'),
('IT000026','Camelya Cube','Wuwa',TRUE,10000,FALSE,FALSE,'/media/images/item/Camelya Cube.gif'),
('IT000027','Carlotta Cube','Wuwa',TRUE,10000,FALSE,FALSE,'/media/images/item/Carlotta Cube.gif');




-- TREEBOX Table
INSERT INTO TREEBOX (TREEBOXID, TREEBOXNAME, TREEBOXCOST, ISDELETED,ISARCHIVED, TREEBOXIMAGE) VALUES
('TB000001', 'Common', 100, FALSE,FALSE, '/media/images/treebox/TB000001.png'),
('TB000002', 'Rare', 300, FALSE,FALSE, '/media/images/treebox/TB000002.png'),
('TB000003', 'Epic', 500, FALSE,FALSE, '/media/images/treebox/TB000003.png'),
('TB000004', 'Legendary', 1500, FALSE,FALSE, '/media/images/treebox/TB000004.png'),
('TB000005', 'Event', 9999999, FALSE,TRUE, '/media/images/treebox/TB000005.png');



-- TREERARITY Table
INSERT INTO TREERARITY (RARITYID, RARITYNAME, RARITYCOLOUR, ISARCHIVED, ISDELETED) VALUES
('TR000001', 'Common', '#A0A0A0', FALSE, FALSE),
('TR000002', 'Rare', '#0070DD', FALSE, FALSE),
('TR000003', 'Epic', '#A335EE', FALSE, FALSE),
('TR000004', 'Legendary', '#FF8000', FALSE, FALSE),
('TR000005', 'Event', '#C0C0C0', FALSE, FALSE);


-- TREE Table
INSERT INTO TREE (TREEID, RARITYID, TREENAME, TREEDESCRIPTION, ISARCHIVED, ISDELETED, TREESTATUS, TREEIMAGE) VALUES
-- Common Trees (TR000001)
('T0000001', 'TR000001', 'Apple Tree', 'A tree that bears delicious apples.', FALSE, FALSE, TRUE, '/media/images/tree/Apple Tree.png'),
('T0000002', 'TR000001', 'Cherry Blossom', 'A beautiful tree with pink blossoms.', FALSE, FALSE, TRUE, '/media/images/tree/Cherry Blossom.png'),
('T0000003', 'TR000001', 'Coconut Palm', 'A tall tropical tree producing coconuts.', FALSE, FALSE, TRUE, '/media/images/tree/Coconut Palm.png'),
('T0000004', 'TR000001', 'Maple Tree', 'A tree with vibrant autumn foliage.', FALSE, FALSE, TRUE, '/media/images/tree/Maple Tree.png'),
('T0000005', 'TR000001', 'Oak Tree', 'A sturdy and majestic tree.', FALSE, FALSE, TRUE, '/media/images/tree/Oak Tree.png'),
('T0000006', 'TR000001', 'Pine Tree', 'An evergreen tree with needle-like leaves.', FALSE, FALSE, TRUE, '/media/images/tree/Pine Tree.png'),
('T0000007', 'TR000001', 'Poplar Tree', 'A fast-growing tree with slender leaves.', FALSE, FALSE, TRUE, '/media/images/tree/Poplar Tree.png'),
('T0000008', 'TR000001', 'White Birch', 'A tree with distinctive white bark.', FALSE, FALSE, TRUE, '/media/images/tree/White Birch.png'),
('T0000009', 'TR000001', 'Willow Tree', 'A tree with graceful, drooping branches.', FALSE, FALSE, TRUE, '/media/images/tree/Willow Tree.png'),
-- Rare Trees (TR000003)
('T0000010', 'TR000002', 'Amethyst Sakura', 'A mystical tree with purple blossoms.', FALSE, FALSE, TRUE, '/media/images/tree/Amethyst Sakura.png'),
('T0000011', 'TR000002', 'Blue Mist Cypress', 'A cypress tree shrouded in blue mist.', FALSE, FALSE, TRUE, '/media/images/tree/Blue Mist Cypress.png'),
('T0000012', 'TR000002', 'Flame Palm', 'A palm tree with fiery red leaves.', FALSE, FALSE, TRUE, '/media/images/tree/Flame Palm.png'),
('T0000013', 'TR000002', 'Golden Maple', 'A Golden Maple Tree.', FALSE, FALSE, TRUE, '/media/images/tree/Golden Maple.png'),
('T0000014', 'TR000002', 'Luminous Birch', 'A birch tree that emits a soft glow at night.', FALSE, FALSE, TRUE, '/media/images/tree/Luminous Birch.png'),
('T0000015', 'TR000002', 'Ruby Pomegranate', 'A tree bearing ruby-like pomegranates.', FALSE, FALSE, TRUE, '/media/images/tree/Ruby Pomegranate.png'),
('T0000016', 'TR000002', 'Silver Olive', 'An olive tree with silvery leaves.', FALSE, FALSE, TRUE, '/media/images/tree/Silver Olive.png'),
('T0000017', 'TR000002', 'Wisteria Orchid', 'A cascading tree with purple flowers.', FALSE, FALSE, TRUE, '/media/images/tree/Wisteria Orchid.png'),
-- Epic Trees (TR000003)
('T0000018', 'TR000003', 'Frostpine', 'An ancient pine tree with frost-covered branches.', FALSE, FALSE, TRUE, '/media/images/tree/Frostpine.png'),
('T0000019', 'TR000003', 'Jade Banyan', 'A banyan tree with jade-green roots.', FALSE, FALSE, TRUE, '/media/images/tree/Jade Banyan.png'),
('T0000020', 'TR000003', 'Moonlight Willow', 'A willow tree that glows under the moonlight.', FALSE, FALSE, TRUE, '/media/images/tree/Moonlight Willow.png'),
('T0000021', 'TR000003', 'Phoenix Wutong', 'A legendary tree associated with the phoenix.', FALSE, FALSE, TRUE, '/media/images/tree/Phoenix Wutong.png'),
('T0000022', 'TR000003', 'Rainbow Eucalyptus', 'A tree with multicolored bark.', FALSE, FALSE, TRUE, '/media/images/tree/Rainbow Eucalyptus.png'),
('T0000023', 'TR000003', 'Thunder Oak', 'A mighty oak tree crackling with energy.', FALSE, FALSE, TRUE, '/media/images/tree/Thunder Oak.png'),
-- Event-Limited Trees
('T0000024', 'TR000005', 'Christmas Miracle', 'A festive tree that glows during the holidays.', FALSE, FALSE, TRUE, '/media/images/tree/Christmas Miracle.png'),
('T0000025', 'TR000005', 'Frostbloom Plum', 'A winter tree that blossoms with icy petals.', FALSE, FALSE, TRUE, '/media/images/tree/Frostbloom Plum.png'),
('T0000026', 'TR000005', 'Halloween Shadow', 'A haunted tree with eerie glowing patterns.', FALSE, FALSE, TRUE, '/media/images/tree/Halloween Shadow.png'),
('T0000027', 'TR000005', 'Netherwood Wraith', 'A spectral tree from the underworld.', FALSE, FALSE, TRUE, '/media/images/tree/Netherwood Wraith.png'),
('T0000028', 'TR000005', 'Sakura Dream', 'A legendary sakura tree seen in dreams.', FALSE, FALSE, TRUE, '/media/images/tree/Sakura Dream.png'),
-- Legendary Trees
('T0000029', 'TR000004', 'Stellar Ancient', 'A cosmic tree connected to the stars.', FALSE, FALSE, TRUE, '/media/images/tree/Stellar Ancient.png'),
('T0000030', 'TR000004', 'Tree of Eternity', 'An eternal tree embodying wisdom and time.', FALSE, FALSE, TRUE, '/media/images/tree/Tree of Eternity.png');



-- QUESTION Table
INSERT INTO QUESTION (QUESTIONID, QUESTIONDESCRIPTION, ISDELETED, ISARCHIVED) VALUES
('Q0000001', 'How are you feeling today?', FALSE, FALSE),
('Q0000002', 'What is a goal you are currently working toward?', FALSE, FALSE),
('Q0000003', 'What is one thing you are grateful for today?', FALSE, FALSE),
('Q0000004', 'If you could give your younger self one piece of advice, what would it be?', FALSE, FALSE),
('Q0000005', 'What does happiness mean to you?', FALSE, FALSE),
('Q0000006', 'How do you handle stress in your daily life?', FALSE, FALSE),
('Q0000007', 'What is something you have recently learned about yourself?', FALSE, FALSE),
('Q0000008', 'What is a challenge you have overcome, and how did it change you?', FALSE, FALSE),
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
INSERT INTO MUSIC (MUSICID, MUSICNAME, AUTHOR, FILEPATH, MHOUR, MMINUTE, MSECOND) VALUES
('M0000001', 'Soothing Relaxation', 'Peder B. Helland', '/media/musics/Soothing Relaxation (Radio Edit).mp3', 0, 2, 18),
('M0000002', 'Sunny Mornings', 'Peder B. Helland', '/media/musics/Sunny Mornings.mp3', 0, 10, 16),
('M0000003', 'The Creek', 'Peder B. Helland', '/media/musics/The Creek.mp3', 0, 7, 22),
('M0000004', 'Forest Whisper', 'Peder B. Helland', '/media/musics/Forest Whisper.mp3', 0, 6, 40),
('M0000005', 'Early in the Morning', 'Peder B. Helland', '/media/musics/Early in the Morning.mp3', 0, 10, 35),
('M0000006', 'Relaxing Music Ocean', 'MUSIC NATION by KENNETH ST.King', '/media/musics/Relaxing Music Ocean.mp3', 0, 2, 45),
('M0000007', 'Enchanted Jungle', 'Nature is Melody', '/media/musics/Enchanted Jungle.mp3', 0, 13, 30),
('M0000008', 'My Rose', 'Peder B. Helland', '/media/musics/My Rose.mp3', 0, 6, 50),
('M0000009', 'Sunny Days', 'Peder B. Helland', '/media/musics/Sunny Days.mp3', 0, 10, 19),
('M0000010', 'Warm Light', 'Peder B. Helland', '/media/musics/Warm Light.mp3', 0, 11, 42),
('M0000011', 'Raindrops', 'Peder B. Helland', '/media/musics/Raindrops.mp3', 0, 8, 3),
('M0000012', 'Rainy Day', 'Peder B. Helland', '/media/musics/Rainy Day.mp3', 0, 13, 2),
('M0000013', 'Yellow Flower', 'Peder B. Helland', '/media/musics/Yellow Flower.mp3', 0, 7, 2),
('M0000014', 'Spring', 'Peder B. Helland', '/media/musics/Spring.mp3', 0, 3, 28),
('M0000015', 'Floating Flower', 'Peder B. Helland', '/media/musics/Floating Flower.mp3', 0, 7, 40),
('M0000016', 'Tapping on Glass', 'JayLynn ASMR', '/media/musics/Tapping on Glass.mp3', 0, 32, 34),
('M0000017', 'Hair Brushing', 'ZenHeads ASMR', '/media/musics/Hair Brushing.mp3', 0, 30, 30),
('M0000018', 'Crinkling Paper', 'TheLozzie88', '/media/musics/Crinkling Paper.mp3', 0, 11, 22),
('M0000019', 'Wooden Triggers', 'Symphony of sleep', '/media/musics/Wooden Triggers.mp3', 0, 8, 32),
('M0000020', 'Water Sounds', 'Cozy Lotus ASMR', '/media/musics/Water Sounds.mp3', 0, 32, 52),
('M0000021', 'Soap & Scratching', 'JayLynn ASMR', '/media/musics/Soap & Scratching.mp3', 0, 19, 57),
('M0000022', 'ASMR MUKBANG', '설기양SULGI', '/media/musics/ASMR MUKBANG.mp3', 0, 17, 6),
('M0000023', 'Short Meditation Music', 'Beautiful Planet Music', '/media/musics/Short Meditation Music.mp3', 0, 3, 7),
('M0000024', 'Deep Healing Music', 'Meditation and Healing', '/media/musics/Deep Healing Music.mp3', 0, 30, 51),
('M0000025', 'Super Deep Meditation Music', 'Yellow Brick Cinema', '/media/musics/Super Deep Meditation Music.mp3', 0, 15, 0),
('M0000026', 'Peaceful Day', 'Peder B. Helland', '/media/musics/Peaceful Day.mp3', 0, 8, 55),
('M0000027', 'Happy Times', 'Peder B. Helland', '/media/musics/Happy Times.mp3', 0, 11, 25),
('M0000028', 'Beautiful Day', 'Peder B. Helland', '/media/musics/Beautiful Day.mp3', 0, 9, 52),
('M0000029', 'The Campfire', 'Peder B. Helland', '/media/musics/The Campfire.mp3', 0, 7, 44),
('M0000030', 'Starship III', 'SEBii & Jiangie', '/media/musics/SEBii & Jiangie_ Starship III (Official Animated Music Video).mp3', 0, 2, 46);




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
INSERT INTO LEVELREWARD (LEVELREWARDID, LEVELID, TREEBOXID, ITEMID, QUANTITY, ISDELETED) VALUES('LR000001',0,'TB000001',NULL,1,FALSE),
('LR000002',0,NULL,'IT000001',1,FALSE),
('LR000003',1,'TB000001',NULL,1,FALSE),
('LR000004',1,NULL,'IT000002',1,FALSE),
('LR000005',2,'TB000001',NULL,1,FALSE),
('LR000006',2,NULL,'IT000003',1,FALSE),
('LR000007',3,'TB000001',NULL,1,FALSE),
('LR000008',3,NULL,'IT000004',1,FALSE),
('LR000009',4,'TB000001',NULL,1,FALSE),
('LR000010',4,NULL,'IT000005',1,FALSE),
('LR000011',5,'TB000001',NULL,5,FALSE),
('LR000012',5,NULL,'IT000006',1,FALSE),
('LR000013',6,'TB000001',NULL,1,FALSE),
('LR000014',6,NULL,'IT000007',1,FALSE),
('LR000015',7,'TB000001',NULL,1,FALSE),
('LR000016',7,NULL,'IT000008',1,FALSE),
('LR000017',8,'TB000001',NULL,1,FALSE),
('LR000018',8,NULL,'IT000009',1,FALSE),
('LR000019',9,'TB000001',NULL,1,FALSE),
('LR000020',9,NULL,'IT000010',1,FALSE),
('LR000021',10,'TB000001',NULL,5,FALSE),
('LR000022',10,NULL,'IT000011',1,FALSE),
('LR000023',11,'TB000001',NULL,1,FALSE),
('LR000024',11,NULL,'IT000012',1,FALSE),
('LR000025',12,'TB000001',NULL,1,FALSE),
('LR000026',12,NULL,'IT000013',1,FALSE),
('LR000027',13,'TB000001',NULL,1,FALSE),
('LR000028',13,NULL,'IT000014',1,FALSE),
('LR000029',14,'TB000001',NULL,1,FALSE),
('LR000030',14,NULL,'IT000015',1,FALSE),
('LR000031',15,'TB000001',NULL,5,FALSE),
('LR000032',15,NULL,'IT000016',1,FALSE),
('LR000033',16,'TB000001',NULL,1,FALSE),
('LR000034',16,NULL,'IT000017',1,FALSE),
('LR000035',17,'TB000001',NULL,1,FALSE),
('LR000036',17,NULL,'IT000018',1,FALSE),
('LR000037',18,'TB000001',NULL,1,FALSE),
('LR000038',18,NULL,'IT000019',1,FALSE),
('LR000039',19,'TB000001',NULL,1,FALSE),
('LR000040',19,NULL,'IT000020',1,FALSE),
('LR000041',20,'TB000001',NULL,5,FALSE),
('LR000042',20,NULL,'IT000021',1,FALSE),
('LR000043',21,'TB000001',NULL,1,FALSE),
('LR000044',21,NULL,'IT000020',1,FALSE),
('LR000045',22,'TB000001',NULL,1,FALSE),
('LR000046',22,NULL,'IT000019',1,FALSE),
('LR000047',23,'TB000001',NULL,1,FALSE),
('LR000048',23,NULL,'IT000018',1,FALSE),
('LR000049',24,'TB000001',NULL,1,FALSE),
('LR000050',24,NULL,'IT000017',1,FALSE),
('LR000051',25,'TB000001',NULL,5,FALSE),
('LR000052',25,NULL,'IT000016',1,FALSE),
('LR000053',26,'TB000001',NULL,1,FALSE),
('LR000054',26,NULL,'IT000017',1,FALSE),
('LR000055',27,'TB000001',NULL,1,FALSE),
('LR000056',27,NULL,'IT000018',1,FALSE),
('LR000057',28,'TB000001',NULL,1,FALSE),
('LR000058',28,NULL,'IT000019',1,FALSE),
('LR000059',29,'TB000001',NULL,1,FALSE),
('LR000060',29,NULL,'IT000020',1,FALSE),
('LR000061',30,'TB000001',NULL,5,FALSE),
('LR000062',30,NULL,'IT000021',1,FALSE);



-- RARITYDROPRATE Table
INSERT INTO RARITYDROPRATE (TREEBOXID, RARITYID, PERCENTAGE, ISDELETED) VALUES
('TB000001', 'TR000001', 70.00, FALSE),
('TB000001', 'TR000002', 20.00, FALSE),
('TB000001', 'TR000003', 8.00, FALSE),
('TB000001', 'TR000004', 2.00, FALSE),
('TB000002','TR000001',30.00,FALSE),
('TB000002','TR000002',50.00,FALSE),
('TB000002','TR000003',15.00,FALSE),
('TB000002','TR000004',5.00,FALSE),
('TB000003','TR000001',15.00,FALSE),
('TB000003','TR000002',35.00,FALSE),
('TB000003','TR000003',30.00,FALSE),
('TB000003','TR000004',20.00,FALSE),
('TB000004','TR000001',10.00,FALSE),
('TB000004','TR000002',25.00,FALSE),
('TB000004','TR000003',35.00,FALSE),
('TB000004','TR000004',30.00,FALSE);


-- USERTASKLIST Table
INSERT INTO USERTASKLIST (USERTASKLISTID, TASKID, USERID, CUSTOMISEDTASKNAME, CUSTOMISEDTASKDESCRIPTION, DATETIMEACCEPTED, DATECOMPLETED, ISDELETED) VALUES
('UTL00001', 'TK000001', 'U2500001', 'Morning Meditation', 'Spend 10 minutes meditating to start the day with clarity.', '2025-03-10 08:00:00', NULL, FALSE),
('UTL00002', 'TK000002', 'U2500001', 'Water the Trees', 'Ensure all trees in your forest are well-watered.', '2025-03-10 09:00:00', NULL, FALSE),
('UTL00003', 'TK000003', 'U2500001', 'Daily Walk', 'Take a 30-minute walk to refresh your mind and body.', '2025-03-10 10:00:00', NULL, FALSE),
('UTL00004', 'TK000004', 'U2500001', 'Read a Chapter', 'Read one chapter of a book to expand your knowledge.', '2025-03-10 11:00:00', NULL, FALSE),
('UTL00005', 'TK000005', 'U2500001', 'Write a Journal Entry', 'Reflect on your day and write about your experiences.', '2025-03-10 12:00:00', NULL, FALSE),
('UTL00006', 'TK000006', 'U2500001', 'Complete a Focus Session', 'Spend 25 minutes working on a deep-focus task.', '2025-03-10 13:00:00', NULL, FALSE),
('UTL00007', 'TK000007', 'U2500001', 'Stretching Routine', 'Do a 10-minute full-body stretching routine.', '2025-03-10 14:00:00', NULL, FALSE),
('UTL00008', 'TK000008', 'U2500001', 'Check-In on Goals', 'Review your progress and adjust your daily goals.', '2025-03-10 15:00:00', NULL, FALSE),
('UTL00009', 'TK000009', 'U2500001', 'Practice Gratitude', 'Write down three things you are grateful for.', '2025-03-10 16:00:00', NULL, FALSE),
('UTL00010', 'TK000010', 'U2500001', 'Night Reflection', 'Take a moment to reflect on what went well today.', '2025-03-10 18:00:00', NULL, FALSE);


-- BIOME Table
INSERT INTO BIOME (BIOMEID, BIOMENAME, BIOMEDESCRIPTION, BIOMECOST, ISDELETED, ISARCHIVED, BIOMEIMAGE) VALUES
('B0000001', 'Forest', 'A lush and vibrant biome brimming with towering trees, chirping birds, and the scent of fresh pine in the air.', 500, FALSE, FALSE, '/media/images/biome/mcgrass.png'),
('B0000002', 'Desert', 'An endless sea of golden sand, scorching sun, and the occasional daring cactus defying the heat.', 300, FALSE, FALSE, '/media/images/biome/mcsand.jpg'),
('B0000003', 'Frozen Islands', 'An icy archipelago cloaked in snow and mystery, where frozen waves crash and polar lights dance overhead.', 800, FALSE, FALSE, '/media/images/biome/mcice.png'),
('B0000004', 'Mountain', 'Soaring peaks that pierce the sky, treacherous cliffs, and whispers of ancient winds echoing through the highlands.', 1000, FALSE, FALSE, '/media/images/biome/mcsnow.jpg'),
('B0000005', 'Sussy Islands', 'Strange islands where nothing is as it seems--hidden vents, mysterious impostors, and sus behaviour around every corner.', 1000, FALSE, FALSE, '/media/images/biome/mcamongus.png'),
('B0000006', 'Pale Garden', 'A hauntingly beautiful swamp shrouded in mist, where pale flowers bloom under a ghostly moonlight and frogs croak like whispers.', 400, TRUE, FALSE, '/media/images/biome/mcpale.jpg');


-- LAND Table
INSERT INTO LAND (LANDID, USERID, BIOMEID, LANDNAME, ISMAINLAND, ISDELETED) VALUES 
('L0000001', 'U2500001', 'B0000001', 'Andrew Forest', TRUE, FALSE),
('L0000002', 'U2500001', 'B0000002', 'Andrew Desert', FALSE, FALSE),
('L0000003', 'U2500001', 'B0000003', 'Andrew Freeze', FALSE, FALSE),
('L0000004', 'U2500001', 'B0000004', 'Andrew Snow', FALSE, FALSE),
('L0000005', 'U2500001', 'B0000005', 'Andrew is SUS', FALSE, FALSE),
('L0000006', 'U2500001', 'B0000001', 'Andrew is Pale', FALSE, FALSE),
('L0000007', 'U2500007', 'B0000002', 'Nancy Desert Oasis', FALSE, FALSE),
('L0000008', 'U2500008', 'B0000003', 'Leah Ocean Paradise', FALSE, FALSE),
('L0000009', 'U2500009', 'B0000004', 'Jason Mountain Base', FALSE, FALSE),
('L0000010', 'U2500010', 'B0000005', 'Sandra Swamp Haven', FALSE, FALSE);


-- LANDCONTENT Table (Currently none)
INSERT INTO LANDCONTENT (LANDCONTENTID, LANDID, ITEMID, TREEID, XCOORD, YCOORD, ISDELETED) VALUES 
('LCAA0001', 'L0000001', 'IT000001', NULL, 10, 20, FALSE),
('LCAA0002', 'L0000001', NULL, 'T0000001', 30, 40, FALSE),
('LCAA0003','L0000001','IT000001',NULL,1,2,FALSE),
('LCAA0004','L0000001',NULL,'T0000001',2,4,FALSE),
('LCAA0005','L0000001',NULL,'T0000001',3,6,FALSE),
('LCAA0006','L0000001','IT000004',NULL,4,8,FALSE),
('LCAA0007','L0000001',NULL,'T0000001',5,5,FALSE),
('LCAA0008','L0000001','IT000002',NULL,6,7,FALSE),
('LCAA0009','L0000001',NULL,'T0000001',7,8,FALSE),
('LCAA0010','L0000001','IT000004',NULL,8,6,FALSE);



-- RESPONE Table
INSERT INTO RESPONSE (RESPONSEID, USERID, QUESTIONID, RESPONSEDESCRIPTION, ISARCHIVED, ISDELETED) VALUES
('R0000001', 'U2500001', 'Q0000001', 'I am grateful for my family and their constant support.', FALSE, FALSE),
('R0000002', 'U2500001', 'Q0000002', 'I would tell my younger self to trust the journey and be patient.', FALSE, FALSE),
('R0000003', 'U2500001', 'Q0000003', 'Happiness is the ability to appreciate the small moments.', FALSE, FALSE),
('R0000004', 'U2500001', 'Q0000004', 'I handle stress by taking deep breaths and focusing on what I can control.', FALSE, FALSE),
('R0000005', 'U2500001', 'Q0000005', 'I recently learned that I am stronger than I thought.', FALSE, FALSE),
('R0000006', 'U2500001', 'Q0000006', 'Overcoming self-doubt has made me more confident in my decisions.', FALSE, FALSE),
('R0000007', 'U2500001', 'Q0000007', 'I would take more risks and not let fear hold me back.', FALSE, FALSE),
('R0000008', 'U2500001', 'Q0000008', 'Today, I will take a break and spend time in nature.', FALSE, FALSE),
('R0000009', 'U2500001', 'Q0000009', 'Yes, I believe everything happens for a reason because it helps me grow.', FALSE, FALSE),
('R0000010', 'U2500001', 'Q0000010', 'Forgiveness is freeing myself from past burdens.', FALSE, FALSE);


-- USERACHIEVEMENT Table
INSERT INTO USERACHIEVEMENT (USERID, ACHIEVEMENTID, DATECOMPLETED, ISDELETED) VALUES
('U2500001', 'A0000001', '2025-03-01', FALSE),
('U2500002', 'A0000002', '2025-03-02', FALSE),
('U2500003', 'A0000003', '2025-03-03', FALSE),
('U2500004', 'A0000004', '2025-03-04', FALSE),
('U2500001', 'A0000005', '2025-03-05', FALSE),
('U2500006', 'A0000001', '2025-03-06', FALSE),
('U2500007', 'A0000002', '2025-03-07', FALSE),
('U2500008', 'A0000003', '2025-03-08', FALSE),
('U2500009', 'A0000004', '2025-03-09', FALSE),
('U2500010', 'A0000005', '2025-03-10', FALSE);


-- USERINVENTORYITEM Table
INSERT INTO USERINVENTORYITEM (INVENTORYITEMID, USERID, TREEBOXID, ITEMID, TREEID, BIOMEID, QUANTITY, ISDELETED) VALUES
('IVAA0001', 'U2500001', 'TB000001', NULL, NULL, NULL, 10, FALSE),
('IVAA0002', 'U2500001', NULL, 'IT000001', NULL, NULL, 5, FALSE),
('IVAA0003', 'U2500001', 'TB000002', NULL, NULL, NULL, 9, FALSE),
('IVAA0004', 'U2500001', NULL, 'IT000002', NULL, NULL, 1, FALSE),
('IVAA0005', 'U2500001', 'TB000003', NULL, NULL, NULL, 8, FALSE),
('IVAA0006', 'U2500001', NULL, 'IT000003', NULL, NULL, 2, FALSE),
('IVAA0007', 'U2500002', 'TB000004', NULL, NULL, NULL, 7, FALSE),
('IVAA0008', 'U2500002', NULL, 'IT000004', NULL, NULL, 3, FALSE),
('IVAA0009', 'U2500002', 'TB000004', NULL, NULL, NULL, 2, FALSE),
('IVAA0010', 'U2500001', NULL, NULL, NULL, 'B0000001', 1, FALSE),
('IVAA0011', 'U2500002', NULL, NULL, NULL, 'B0000001', 1, FALSE),
('IVAA0012', 'U2500001', NULL, NULL, NULL, 'B0000002', 1, FALSE),
('IVAA0013','U2500001',NULL,NULL,'T0000001',NULL,9,FALSE),
('IVAA0014','U2500001',NULL,NULL,'T0000002',NULL,1,FALSE),
('IVAA0015','U2500001',NULL,NULL,'T0000003',NULL,3,FALSE),
('IVAA0016','U2500001',NULL,NULL,'T0000004',NULL,5,FALSE),
('IVAA0017','U2500001',NULL,NULL,'T0000005',NULL,2,FALSE),
('IVAA0018','U2500001',NULL,NULL,'T0000006',NULL,4,FALSE),
('IVAA0019','U2500001',NULL,NULL,'T0000007',NULL,5,FALSE),
('IVAA0020','U2500001',NULL,NULL,'T0000008',NULL,3,FALSE),
('IVAA0021','U2500001',NULL,NULL,'T0000009',NULL,2,FALSE),
('IVAA0022','U2500001',NULL,'IT000022',NULL,NULL,2,FALSE),
('IVAA0023','U2500001',NULL,'IT000023',NULL,NULL,2,FALSE),
('IVAA0024','U2500001',NULL,'IT000024',NULL,NULL,2,FALSE),
('IVAA0025','U2500001',NULL,'IT000025',NULL,NULL,2,FALSE),
('IVAA0026','U2500001',NULL,'IT000026',NULL,NULL,2,FALSE),
('IVAA0027','U2500001',NULL,'IT000027',NULL,NULL,2,FALSE),
('IVAA0028','U2500001','TB000004',NULL,NULL,NULL,5,FALSE);




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
('U2500001', 'BA000001', FALSE, FALSE),
('U2500006', 'BA000003', FALSE, FALSE),
('U2500007', 'BA000001', TRUE, FALSE),
('U2500008', 'BA000003', FALSE, FALSE),
('U2500009', 'BA000003', TRUE, FALSE),
('U2500010', 'BA000004', FALSE, FALSE);


-- USERPLAYLIST Table
-- Insert into USERPLAYLIST
INSERT INTO USERPLAYLIST (PLAYLISTID, USERID, PLAYLISTNAME, DATECREATED, ISDELETED) VALUES
('PL000001', 'U2500001', 'Nature Sounds', '2025-02-01', FALSE),
('PL000002', 'U2500001', 'ASMR', '2025-02-01', FALSE),
('PL000003', 'U2500001', 'Relaxing Music', '2025-02-01', FALSE);



-- USERFAVOURITE Table
INSERT INTO USERFAVOURITE (USERID, MUSICID, ISDELETED) VALUES 
('U2500001', 'M0000001', FALSE),
('U2500001', 'M0000003', FALSE),
('U2500001', 'M0000005', FALSE);


-- PLAYLISTMUSIC Table
-- Insert into PLAYLISTMUSIC for Nature Sounds playlist (PL000001)
INSERT INTO PLAYLISTMUSIC (PLAYLISTID, MUSICID, ISDELETED) VALUES
('PL000001', 'M0000001', FALSE),  -- Soothing Relaxation (Radio Edit)
('PL000001', 'M0000002', FALSE),  -- Sunny Mornings
('PL000001', 'M0000003', FALSE),  -- The Creek
('PL000001', 'M0000004', FALSE),  -- Forest Whisper
('PL000001', 'M0000005', FALSE),  -- Early in the Morning
('PL000001', 'M0000006', FALSE),  -- Relaxing Music Ocean
('PL000001', 'M0000007', FALSE),  -- Enchanted Jungle
('PL000001', 'M0000008', FALSE),  -- My Rose
('PL000001', 'M0000009', FALSE),  -- Sunny Days
('PL000001', 'M0000010', FALSE),  -- Warm Light
('PL000001', 'M0000011', FALSE),  -- Raindrops
('PL000001', 'M0000012', FALSE),  -- Rainy Day
('PL000001', 'M0000013', FALSE),  -- Yellow Flower
('PL000001', 'M0000014', FALSE),  -- Spring
('PL000001', 'M0000015', FALSE);  -- Floating Flower

-- Insert into PLAYLISTMUSIC for ASMR playlist (PL000002)
INSERT INTO PLAYLISTMUSIC (PLAYLISTID, MUSICID, ISDELETED) VALUES
('PL000002', 'M0000016', FALSE),  -- Tapping on Glass
('PL000002', 'M0000017', FALSE),  -- Hair Brushing
('PL000002', 'M0000018', FALSE),  -- Crinkling Paper
('PL000002', 'M0000019', FALSE),  -- Wooden Triggers
('PL000002', 'M0000020', FALSE),  -- Water Sounds
('PL000002', 'M0000021', FALSE),  -- Soap & Scratching
('PL000002', 'M0000022', FALSE);  -- ASMR MUKBANG

-- Insert into PLAYLISTMUSIC for Relaxing Music playlist (PL000003)
INSERT INTO PLAYLISTMUSIC (PLAYLISTID, MUSICID, ISDELETED) VALUES
('PL000003', 'M0000023', FALSE),  -- Short Meditation Music
('PL000003', 'M0000024', FALSE),  -- Deep Healing Music for The Body & Soul
('PL000003', 'M0000025', FALSE),  -- Super Deep Meditation Music
('PL000003', 'M0000026', FALSE),  -- Peaceful Day
('PL000003', 'M0000027', FALSE),  -- Happy Times
('PL000003', 'M0000028', FALSE),  -- Beautiful Day
('PL000003', 'M0000029', FALSE),  -- The Campfire
('PL000003', 'M0000030', FALSE);  -- Starship III




-- FOCUSSESSION Table
INSERT INTO FOCUSSESSION (SESSIONID, USERID, SESSIONTYPE, DURATION, POMODOROMINORBREAK, POMODOROMAJORBREAK, SESSIONSTATUS, TREEBOXESOBTAINED, ISDELETED) VALUES
('SAA00001', 'U2500001', 'Pomodoro', 25.00, 5, 15, 'Completed', 1, FALSE),
('SAA00002', 'U2500002', 'Deep Work', 60.00, NULL, NULL, 'Completed', 2, FALSE),
('SAA00003', 'U2500003', 'Pomodoro', 50.00, 5, 15, 'Completed', 1, FALSE),
('SAA00004', 'U2500004', 'Custom', 75.00, NULL, NULL, 'In Progress', 0, FALSE),
('SAA00005', 'U2500001', 'Pomodoro', 25.00, 5, 15, 'Completed', 1, FALSE),
('SAA00006', 'U2500006', 'Deep Work', 90.00, NULL, NULL, 'Completed', 3, FALSE),
('SAA00007', 'U2500007', 'Custom', 45.00, NULL, NULL, 'Completed', 2, FALSE),
('SAA00008', 'U2500008', 'Pomodoro', 30.00, 5, 15, 'Completed', 1, FALSE),
('SAA00009', 'U2500009', 'Deep Work', 120.00, NULL, NULL, 'Completed', 4, FALSE),
('SAA00010', 'U2500010', 'Custom', 60.00, NULL, NULL, 'Completed', 2, FALSE);


-- DIARYENTRY Table (50 entries, valid moods only)
INSERT INTO DIARYENTRY (DIARYID, USERID, DIARYTITLE, DESCRIPTION, MOOD, DATEWRITTEN, ISARCHIVED, ISDELETED) VALUES
('D0000001', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-01', FALSE, FALSE),
('D0000002', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Excited', '2025-01-02', FALSE, FALSE),
('D0000003', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Sad', '2025-01-03', FALSE, FALSE),
('D0000004', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-03', FALSE, FALSE),
('D0000005', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Sad', '2025-01-04', FALSE, FALSE),
('D0000006', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-05', FALSE, FALSE),
('D0000007', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-05', FALSE, FALSE),
('D0000008', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-06', FALSE, FALSE),
('D0000009', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-06', FALSE, FALSE),
('D0000010', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Excited', '2025-01-07', TRUE, FALSE),
('D0000011', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-09', FALSE, FALSE),
('D0000012', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Excited', '2025-01-10', FALSE, FALSE),
('D0000013', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Happy', '2025-01-10', FALSE, FALSE),
('D0000014', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-11', FALSE, FALSE),
('D0000015', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-11', FALSE, FALSE),
('D0000016', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Happy', '2025-01-12', TRUE, FALSE),
('D0000017', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-12', FALSE, FALSE),
('D0000018', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-13', FALSE, FALSE),
('D0000019', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-13', TRUE, FALSE),
('D0000020', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-13', FALSE, FALSE),
('D0000021', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-14', FALSE, FALSE),
('D0000022', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Excited', '2025-01-14', FALSE, FALSE),
('D0000023', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-16', FALSE, FALSE),
('D0000024', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-18', TRUE, FALSE),
('D0000025', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-18', FALSE, FALSE),
('D0000026', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-18', TRUE, FALSE),
('D0000027', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-18', FALSE, FALSE),
('D0000028', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-19', FALSE, FALSE),
('D0000029', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-20', TRUE, FALSE),
('D0000030', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Excited', '2025-01-20', TRUE, FALSE),
('D0000031', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-21', FALSE, FALSE),
('D0000032', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-21', FALSE, FALSE),
('D0000033', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Sad', '2025-01-21', FALSE, FALSE),
('D0000034', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Sad', '2025-01-21', FALSE, FALSE),
('D0000035', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-22', FALSE, FALSE),
('D0000036', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-23', FALSE, FALSE),
('D0000037', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Sad', '2025-01-23', TRUE, FALSE),
('D0000038', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-23', FALSE, FALSE),
('D0000039', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-24', FALSE, FALSE),
('D0000040', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Angry', '2025-01-24', FALSE, FALSE),
('D0000041', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-24', FALSE, FALSE),
('D0000042', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-25', FALSE, FALSE),
('D0000043', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-25', FALSE, FALSE),
('D0000044', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Excited', '2025-01-26', TRUE, FALSE),
('D0000045', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-27', FALSE, FALSE),
('D0000046', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Frustrated', '2025-01-27', TRUE, FALSE),
('D0000047', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Calm', '2025-01-28', FALSE, FALSE),
('D0000048', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-28', FALSE, FALSE),
('D0000049', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Sad', '2025-01-28', FALSE, FALSE),
('D0000050', 'U2500001', 'Sample Title', 'This is a sample diary entry for January.', 'Neutral', '2025-01-31', FALSE, FALSE),
('D0000051', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-01', TRUE, FALSE),
('D0000052', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-02', TRUE, FALSE),
('D0000053', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-02', FALSE, FALSE),
('D0000054', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-03', TRUE, FALSE),
('D0000055', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-04', FALSE, FALSE),
('D0000056', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-04', FALSE, FALSE),
('D0000057', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-04', FALSE, FALSE),
('D0000058', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Neutral', '2025-02-05', FALSE, FALSE),
('D0000059', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-05', FALSE, FALSE),
('D0000060', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-06', FALSE, FALSE),
('D0000061', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-06', FALSE, FALSE),
('D0000062', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-07', FALSE, FALSE),
('D0000063', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-08', FALSE, FALSE),
('D0000064', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Neutral', '2025-02-08', FALSE, FALSE),
('D0000065', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-09', TRUE, FALSE),
('D0000066', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Neutral', '2025-02-09', FALSE, FALSE),
('D0000067', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-10', FALSE, FALSE),
('D0000068', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-11', TRUE, FALSE),
('D0000069', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-11', TRUE, FALSE),
('D0000070', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-11', TRUE, FALSE),
('D0000071', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-11', FALSE, FALSE),
('D0000072', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-12', TRUE, FALSE),
('D0000073', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-12', FALSE, FALSE),
('D0000074', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-13', FALSE, FALSE),
('D0000075', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-14', FALSE, FALSE),
('D0000076', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-14', FALSE, FALSE),
('D0000077', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-14', TRUE, FALSE),
('D0000078', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Neutral', '2025-02-14', FALSE, FALSE),
('D0000079', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-15', FALSE, FALSE),
('D0000080', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-15', FALSE, FALSE),
('D0000081', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-16', TRUE, FALSE),
('D0000082', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-16', FALSE, FALSE),
('D0000083', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-16', TRUE, FALSE),
('D0000084', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-17', TRUE, FALSE),
('D0000085', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-17', FALSE, FALSE),
('D0000086', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Calm', '2025-02-18', FALSE, FALSE),
('D0000087', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-18', TRUE, FALSE),
('D0000088', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-20', TRUE, FALSE),
('D0000089', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Sad', '2025-02-20', FALSE, FALSE),
('D0000090', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-21', FALSE, FALSE),
('D0000091', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Frustrated', '2025-02-21', FALSE, FALSE),
('D0000092', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-21', FALSE, FALSE),
('D0000093', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-22', FALSE, FALSE),
('D0000094', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-22', TRUE, FALSE),
('D0000095', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Happy', '2025-02-23', FALSE, FALSE),
('D0000096', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-24', FALSE, FALSE),
('D0000097', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Angry', '2025-02-24', FALSE, FALSE),
('D0000098', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-26', FALSE, FALSE),
('D0000099', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Excited', '2025-02-26', FALSE, FALSE),
('D0000100', 'U2500001', 'Sample Title', 'This is a sample diary entry for February.', 'Neutral', '2025-02-27', TRUE, FALSE),
('D0000101', 'U2500001', 'New Month Begins', 'March feels full of potential. Let us make it count.', 'Excited', '2025-03-01', FALSE, FALSE),
('D0000102', 'U2500001', 'Grocery Morning', 'Restocked food and supplies.', 'Neutral', '2025-03-01', TRUE, FALSE),
('D0000103', 'U2500001', 'Sunday Read', 'Finished a book I started weeks ago.', 'Calm', '2025-03-02', FALSE, FALSE),
('D0000104', 'U2500001', 'System Downtime', 'Server was down for hours. Stressful.', 'Frustrated', '2025-03-02', FALSE, FALSE),
('D0000105', 'U2500001', 'Test Case Win', 'One of my test suites finally passed!', 'Happy', '2025-03-03', TRUE, FALSE),
('D0000106', 'U2500001', 'Coffee Break', 'Tried a new blend at the café.', 'Calm', '2025-03-03', FALSE, FALSE),
('D0000107', 'U2500001', 'Debug Nightmare', 'Could not find the root cause. Need rest.', 'Angry', '2025-03-04', FALSE, FALSE),
('D0000108', 'U2500001', 'Tired Evening', 'Could not focus at all today.', 'Sad', '2025-03-04', FALSE, FALSE),
('D0000109', 'U2500001', 'Call from Home', 'Talked to my sister. Felt warm.', 'Happy', '2025-03-05', FALSE, FALSE),
('D0000110', 'U2500001', 'Planning Session', 'Mapped out tasks for the week.', 'Neutral', '2025-03-05', FALSE, FALSE),
('D0000111', 'U2500001', 'Focused Morning', 'Made great progress on my prototype.', 'Excited', '2025-03-06', TRUE, FALSE),
('D0000112', 'U2500001', 'Slow Response', 'Laptop froze during a meeting.', 'Frustrated', '2025-03-06', TRUE, FALSE),
('D0000113', 'U2500001', 'Workspace Tidy', 'Cleaned my desk. Feels nice.', 'Happy', '2025-03-07', FALSE, FALSE),
('D0000114', 'U2500001', 'Silent Day', 'Did not talk to anyone. Peaceful.', 'Calm', '2025-03-07', FALSE, FALSE),
('D0000115', 'U2500001', 'Too Many Meetings', 'No room for deep work.', 'Frustrated', '2025-03-08', TRUE, FALSE),
('D0000116', 'U2500001', 'Thinking Ahead', 'Brainstormed ideas for April.', 'Neutral', '2025-03-08', TRUE, FALSE),
('D0000117', 'U2500001', 'Lazy Saturday', 'Stayed in bed and watched shows.', 'Neutral', '2025-03-09', FALSE, FALSE),
('D0000118', 'U2500001', 'Walk in Park', 'The weather was perfect.', 'Calm', '2025-03-09', TRUE, FALSE),
('D0000119', 'U2500001', 'Unexpected Email', 'Got a scholarship opportunity email.', 'Excited', '2025-03-10', FALSE, FALSE),
('D0000120', 'U2500001', 'Coding Flow', 'No distractions today. Nailed it.', 'Happy', '2025-03-10', FALSE, FALSE),
('D0000121', 'U2500001', 'Anxious Mood', 'Too many deadlines coming soon.', 'Sad', '2025-03-11', FALSE, FALSE),
('D0000122', 'U2500001', 'Notebook Filled', 'Finished my current journal.', 'Neutral', '2025-03-11', TRUE, FALSE),
('D0000123', 'U2500001', 'Early Start', 'Woke up before the alarm.', 'Excited', '2025-03-12', TRUE, FALSE),
('D0000124', 'U2500001', 'Skipped Lunch', 'Meetings ran over again.', 'Angry', '2025-03-12', TRUE, FALSE),
('D0000125', 'U2500001', 'Evening Rain', 'Relaxed to the sound of rain.', 'Calm', '2025-03-13', FALSE, FALSE),
('D0000126', 'U2500001', 'Lost USB', 'Important files missing.', 'Frustrated', '2025-03-13', FALSE, FALSE),
('D0000127', 'U2500001', 'Sprint Retrospective', 'Good feedback from the team.', 'Happy', '2025-03-14', TRUE, FALSE),
('D0000128', 'U2500001', 'Minor Arguments', 'Team disagreed on design choices.', 'Angry', '2025-03-14', FALSE, FALSE),
('D0000129', 'U2500001', 'Quiet Night', 'Read articles and unplugged early.', 'Calm', '2025-03-15', FALSE, FALSE),
('D0000130', 'U2500001', 'Design Block', 'Stuck on UI layout ideas.', 'Frustrated', '2025-03-15', FALSE, FALSE),
('D0000131', 'U2500001', 'Short Nap', 'Midday nap saved me.', 'Neutral', '2025-03-16', TRUE, FALSE),
('D0000132', 'U2500001', 'Weekend Chill', 'Listened to podcasts and drank tea.', 'Calm', '2025-03-16', FALSE, FALSE),
('D0000133', 'U2500001', 'Productive Monday', 'Started strong this week.', 'Happy', '2025-03-17', FALSE, FALSE),
('D0000134', 'U2500001', 'Slow Network', 'Wifi issues all day.', 'Frustrated', '2025-03-17', FALSE, FALSE),
('D0000135', 'U2500001', 'Code Reviewed', 'Reviewed my friend code.', 'Neutral', '2025-03-18', FALSE, FALSE),
('D0000136', 'U2500001', 'Weird Mood', 'Felt off for no reason.', 'Sad', '2025-03-18', FALSE, FALSE),
('D0000137', 'U2500001', 'Focus Playlist', 'Made a playlist that boosts focus.', 'Happy', '2025-03-19', FALSE, FALSE),
('D0000138', 'U2500001', 'Quick Exercise', 'Did 20 push-ups and felt great.', 'Excited', '2025-03-19', TRUE, FALSE),
('D0000139', 'U2500001', 'Halfway Point', 'March is going fast.', 'Neutral', '2025-03-20', FALSE, FALSE),
('D0000140', 'U2500001', 'Overthinking Again', 'Mind would not stop running.', 'Sad', '2025-03-20', TRUE, FALSE),
('D0000141', 'U2500001', 'Late Night Ideas', 'Wrote new features for the app.', 'Excited', '2025-03-21', FALSE, FALSE),
('D0000142', 'U2500001', 'Mood Swings', 'Too emotional today.', 'Sad', '2025-03-21', FALSE, FALSE),
('D0000143', 'U2500001', 'Good News', 'Bug was on server, not my code.', 'Happy', '2025-03-22', FALSE, FALSE),
('D0000144', 'U2500001', 'Rain and Tea', 'Peaceful afternoon at home.', 'Calm', '2025-03-22', TRUE, FALSE),
('D0000145', 'U2500001', 'Power Outage', 'Lost 2 hours of unsaved work.', 'Angry', '2025-03-23', FALSE, FALSE),
('D0000146', 'U2500001', 'Simple Dinner', 'Cooked pasta.', 'Neutral', '2025-03-23', FALSE, FALSE),
('D0000147', 'U2500001', 'Back to Reading', 'Started a new book.', 'Calm', '2025-03-24', TRUE, FALSE),
('D0000148', 'U2500001', 'Research Ideas', 'Wrote some notes for Chapter 2.', 'Neutral', '2025-03-24', FALSE, FALSE),
('D0000149', 'U2500001', 'Long Walk', 'Stretched my legs and felt better.', 'Happy', '2025-03-25', TRUE, FALSE),
('D0000150', 'U2500001', 'Late Submission', 'Barely submitted the report in time.', 'Frustrated', '2025-03-25', TRUE, FALSE),
('D0000151', 'U2500001', 'Morning Jog', 'Started the day with a 5km jog. Felt energized and clear-headed.', 'Happy', '2025-04-01', TRUE, FALSE),
('D0000152', 'U2500001', 'Code Debugging', 'Spent hours fixing a persistent bug. Finally got it resolved!', 'Frustrated', '2025-04-01', FALSE, FALSE),
('D0000153', 'U2500001', 'Rainy Mood', 'It rained all afternoon. Stayed in and listened to music.', 'Calm', '2025-04-02', TRUE, FALSE),
('D0000154', 'U2500001', 'Study Grind', 'Focused on reading Agile testing strategies. Good progress.', 'Neutral', '2025-04-02', TRUE, FALSE),
('D0000155', 'U2500001', 'Unexpected Visit', 'An old friend dropped by today. Great to catch up!', 'Happy', '2025-04-03', FALSE, FALSE),
('D0000156', 'U2500001', 'FYP Planning', 'Started sketching out the FYP timeline.', 'Neutral', '2025-04-03', TRUE, FALSE),
('D0000157', 'U2500001', 'Overslept Again', 'Woke up late and missed a meeting. Not my best day.', 'Frustrated', '2025-04-04', FALSE, FALSE),
('D0000158', 'U2500001', 'Proposal Submitted', 'Finally submitted the FYP proposal. One step forward!', 'Excited', '2025-04-04', FALSE, FALSE),
('D0000159', 'U2500001', 'Offline Day', 'Stayed offline all day. Much needed digital detox.', 'Calm', '2025-04-05', FALSE, FALSE),
('D0000160', 'U2500001', 'Café Writing', 'Discovered a cozy café and wrote for an hour.', 'Happy', '2025-04-05', FALSE, FALSE),
('D0000161', 'U2500001', 'Grocery Run', 'Did a full grocery restock.', 'Neutral', '2025-04-06', TRUE, FALSE),
('D0000162', 'U2500001', 'Sketching Again', 'Drew a forest scene. Missed doing this.', 'Calm', '2025-04-06', FALSE, FALSE),
('D0000163', 'U2500001', 'System Reading', 'Went through system architecture materials.', 'Neutral', '2025-04-07', FALSE, FALSE),
('D0000164', 'U2500001', 'Evening Walk', 'Walked after dinner. The breeze was perfect.', 'Calm', '2025-04-07', TRUE, FALSE),
('D0000165', 'U2500001', 'Group Chaos', 'Group project chat blew up with last-minute changes.', 'Angry', '2025-04-08', TRUE, FALSE),
('D0000166', 'U2500001', 'Break Time', 'Took the evening off. Watched a comedy.', 'Happy', '2025-04-08', FALSE, FALSE),
('D0000167', 'U2500001', 'Mockup Done', 'Finished UI mockup for driver system.', 'Neutral', '2025-04-09', FALSE, FALSE),
('D0000168', 'U2500001', 'Quick Workout', 'Did a 20-minute HIIT session.', 'Excited', '2025-04-09', FALSE, FALSE),
('D0000169', 'U2500001', 'Bad Sleep', 'Kept waking up. Brain fog all day.', 'Sad', '2025-04-10', TRUE, FALSE),
('D0000170', 'U2500001', 'Midnight Thoughts', 'Started journaling deep stuff at 12AM.', 'Sad', '2025-04-10', TRUE, FALSE),
('D0000171', 'U2500001', 'Library Session', 'Studied at the library today.', 'Neutral', '2025-04-11', FALSE, FALSE),
('D0000172', 'U2500001', 'Treat Time', 'Bought bubble tea after a long session.', 'Happy', '2025-04-11', TRUE, FALSE),
('D0000173', 'U2500001', 'Trip Planning', 'Planning a short weekend trip.', 'Excited', '2025-04-12', FALSE, FALSE),
('D0000174', 'U2500001', 'Laundry Time', 'Finally tackled the laundry pile.', 'Neutral', '2025-04-12', FALSE, FALSE),
('D0000175', 'U2500001', 'Coding Marathon', 'Coded for 5 hours straight.', 'Frustrated', '2025-04-13', FALSE, FALSE),
('D0000176', 'U2500001', 'Minor Setback', 'Test case failed.', 'Frustrated', '2025-04-13', FALSE, FALSE),
('D0000177', 'U2500001', 'Team Meeting', 'Met with project mates.', 'Neutral', '2025-04-14', FALSE, FALSE),
('D0000178', 'U2500001', 'Java Practice', 'Solved new problems.', 'Happy', '2025-04-14', TRUE, FALSE),
('D0000179', 'U2500001', 'Cold Morning', 'Weather is chilly. Love it.', 'Calm', '2025-04-15', FALSE, FALSE),
('D0000180', 'U2500001', 'Lazy Day', 'Watched anime all day.', 'Neutral', '2025-04-15', TRUE, FALSE),
('D0000181', 'U2500001', 'Sprint Planning', 'Planned this week tasks.', 'Neutral', '2025-04-16', FALSE, FALSE),
('D0000182', 'U2500001', 'Missed Lunch', 'Too caught up in debugging.', 'Angry', '2025-04-16', FALSE, FALSE),
('D0000183', 'U2500001', 'Tag Review', 'Reviewed diary tags.', 'Neutral', '2025-04-17', FALSE, FALSE),
('D0000184', 'U2500001', 'Feeling Alone', 'Did not talk to anyone today.', 'Sad', '2025-04-17', FALSE, FALSE),
('D0000185', 'U2500001', 'Chapter 1 Done', 'Wrapped up the FYP intro.', 'Happy', '2025-04-18', TRUE, FALSE),
('D0000186', 'U2500001', 'Called Family', 'Talked to mom today.', 'Happy', '2025-04-18', FALSE, FALSE),
('D0000187', 'U2500001', 'Overthinking', 'Mind replayed past stuff.', 'Sad', '2025-04-19', FALSE, FALSE),
('D0000188', 'U2500001', 'Weekend Nap', 'Slept for 3 hours midday.', 'Calm', '2025-04-19', FALSE, FALSE),
('D0000189', 'U2500001', 'Research Start', 'Compiled background studies.', 'Neutral', '2025-04-20', TRUE, FALSE),
('D0000190', 'U2500001', 'Quiet Day', 'Worked with lo-fi beats.', 'Calm', '2025-04-20', TRUE, FALSE),
('D0000191', 'U2500001', 'No WiFi', 'Worked offline all day.', 'Frustrated', '2025-04-21', FALSE, FALSE),
('D0000192', 'U2500001', 'Back Online', 'Finally synced files.', 'Happy', '2025-04-21', FALSE, FALSE),
('D0000193', 'U2500001', 'Bug Squashed', 'Last bug is dead.', 'Excited', '2025-04-22', TRUE, FALSE),
('D0000194', 'U2500001', 'Late Dinner', 'Ate at 9PM.', 'Neutral', '2025-04-22', FALSE, FALSE),
('D0000195', 'U2500001', 'Good Sleep', '8 hours of rest.', 'Calm', '2025-04-23', FALSE, FALSE),
('D0000196', 'U2500001', 'Clean Desk', 'Tidied up everything.', 'Happy', '2025-04-23', FALSE, FALSE),
('D0000197', 'U2500001', 'New Playlist', 'Created a coding playlist.', 'Neutral', '2025-04-24', FALSE, FALSE),
('D0000198', 'U2500001', 'Procrastinated', 'Scrolled too much.', 'Frustrated', '2025-04-24', FALSE, FALSE),
('D0000199', 'U2500001', 'Feeling Hopeful', 'Prepared for presentation.', 'Excited', '2025-04-25', TRUE, FALSE),
('D0000200', 'U2500001', 'April Reflections', 'This month taught me a lot.', 'Calm', '2025-04-30', TRUE, FALSE);







-- THREADCATEGORY Table
INSERT INTO THREADCATEGORY (THREADCATEGORYID, THREADCATEGORYNAME, ISDELETED) VALUES
('THC00001', 'General Discussion', FALSE),
('THC00002', 'Productivity Tips', FALSE),
('THC00003', 'Feature Requests', FALSE),
('THC00004', 'Bug Reports', FALSE),
('THC00005', 'Mental Wellness', FALSE),
('THC00006', 'Others', FALSE);


-- THREAD Table
INSERT INTO THREAD (THREADID, USERID, THREADCATEGORYID, THREADTITLE, THREADDESCRIPTION, UPVOTE, DOWNVOTE, SHARECOUNT, ISDELETED) VALUES
('TH000001', 'U2500001', 'THC00001', 'How do you stay productive?', 'Share your best productivity hacks!', 10, 0, 5, FALSE),
('TH000002', 'U2500002', 'THC00002', 'Best Pomodoro Timer Apps?', 'Looking for recommendations for Pomodoro apps!', 15, 1, 8, FALSE),
('TH000003', 'U2500003', 'THC00003', 'New Feature: Custom Tree Designs?', 'Would love to have custom tree options in the game!', 25, 3, 12, FALSE),
('TH000004', 'U2500004', 'THC00004', 'Game Crashes on Start', 'Has anyone experienced crashes when launching Mizuki Forest?', 5, 10, 3, FALSE),
('TH000005', 'U2500001', 'THC00005', 'Daily Gratitude Thread', 'Share one thing you are grateful for today!', 30, 0, 20, FALSE),
('TH000006', 'U2500001', 'THC00001', 'What is Your Daily Routine Like?', 'Share a how you structure your day.', 10, 1, 11, FALSE),
('TH000007', 'U2500001', 'THC00002', 'Small Habits That Make a Big Difference', 'What little things help you stay consistent and productive?', 40, 2, 9, FALSE),
('TH000008', 'U2500001', 'THC00005', 'How Do You Unwind After a Long Day?', 'Share your go-to methods for relaxing and recharging', 26, 3, 8, FALSE),
('TH000009', 'U2500001', 'THC00002', 'Staying Focused in a Distracted World', 'Talk about how you minimize distractions and stay on track.', 37, 6, 16, FALSE),
('TH000010', 'U2500001', 'THC00006', 'Why Waking Up Early Is Just Stupid', 'What is your wake up time.', 37, 6, 16, FALSE);

-- THREADIMAGE Table
INSERT INTO THREADIMAGE (IMAGEID, THREADID, ISMAINIMAGE, ISDELETED, IMAGE) VALUES
('IA000001', 'TH000001', TRUE, FALSE, NULL),
('IA000002', 'TH000002', TRUE, FALSE, NULL),
('IA000003', 'TH000003', TRUE, FALSE, NULL),
('IA000004', 'TH000004', TRUE, FALSE, NULL),
('IA000005', 'TH000005', TRUE, FALSE, NULL);


-- THREADCOMMENT Table
INSERT INTO THREADCOMMENT (THREADCOMMENTID, THREADID, USERID, COMMENTIDREPLYINGTO, POSTDATETIME, CONTENT, UPVOTE, DOWNVOTE, ISDELETED) VALUES
('C0000001', 'TH000001', 'U2500002', NULL, '2025-03-01 08:00:00', 'I use the Pomodoro Technique, 25 minutes of focused work followed by 5-minute breaks.', 7, 0, FALSE),
('C0000002', 'TH000002', 'U2500003', NULL, '2025-03-01 09:00:00', 'I recommend Forest and Focus To-Do, they work great!', 12, 1, FALSE),
('C0000003', 'TH000003', 'U2500004', NULL, '2025-03-01 10:00:00', 'Custom trees would be amazing! Maybe unlockable ones?', 18, 2, FALSE),
('C0000004', 'TH000004', 'U2500001', NULL, '2025-03-01 11:00:00', 'I had the same issue, reinstalling the game fixed it for me.', 4, 5, FALSE),
('C0000005', 'TH000005', 'U2500001', NULL, '2025-03-01 12:00:00', 'I am grateful for the sunny weather today!', 10, 0, FALSE),
('C0000006', 'TH000001', 'U2500001', 'C0000001', '2025-03-01 13:00:00', 'That technique helped me a lot during exam season. The short breaks made studying feel less overwhelming.', 10, 0, FALSE),
('C0000007', 'TH000001', 'U2500003', 'C0000001', '2025-03-01 14:00:00', 'I have found that combining Pomodoro with lo-fi music creates a really calming work environment.', 10, 0, FALSE),
('C0000008', 'TH000001', 'U2500001', NULL, '2025-03-01 15:00:00', 'I stay productive by planning the next day every evening.', 10, 0, FALSE),
('C0000009', 'TH000010', 'U2500004', NULL, '2025-03-01 16:00:00', 'You are all doing it wrong!!!', 2, 32, FALSE),
('C0000010', 'TH000010', 'U2500004', NULL, '2025-03-01 17:00:00', 'This thread is so dumb', 1, 21, FALSE);

-- THREADVOTE Table
INSERT INTO THREADVOTE (THREADID, USERID, VOTETYPE) VALUES
('TH000001', 'U2500001', TRUE),
('TH000002', 'U2500002', FALSE),
('TH000003', 'U2500003', TRUE),
('TH000004', 'U2500004', FALSE),
('TH000005', 'U2500001', TRUE);


-- COMMENTVOTE Table
INSERT INTO COMMENTVOTE (THREADCOMMENTID, USERID, VOTETYPE) VALUES
('C0000001', 'U2500001', TRUE),
('C0000002', 'U2500002', FALSE),
('C0000003', 'U2500003', TRUE),
('C0000004', 'U2500004', FALSE),
('C0000005', 'U2500001', TRUE);


-- USERTHERAPIST Table
INSERT INTO USERTHERAPIST (USERTHERAPISTID, USERID, THERAPISTID, DATEESTABLISHED, DATEEND, ISDELETED) VALUES
('UT000001', 'U2500001', 'TP000001', '2025-03-01', NULL, FALSE),
('UT000002', 'U2500002', 'TP000002', '2025-02-15', '2025-03-15', FALSE),
('UT000003', 'U2500003', 'TP000003', '2025-01-10', NULL, FALSE),
('UT000004', 'U2500004', 'TP000004', '2025-03-05', NULL, FALSE),
('UT000005', 'U2500001', 'TP000005', '2025-02-01', NULL, FALSE),
('UT000006', 'U2500006', 'TP000006', '2025-03-08', NULL, FALSE),
('UT000007', 'U2500007', 'TP000007', '2025-02-20', '2025-03-20', FALSE),
('UT000008', 'U2500008', 'TP000008', '2025-01-25', NULL, FALSE),
('UT000009', 'U2500009', 'TP000009', '2025-03-02', NULL, FALSE),
('UT000010', 'U2500010', 'TP000010', '2025-02-28', NULL, FALSE);


-- MESSAGE Table
INSERT INTO MESSAGE (MESSAGEID, USERID, THERAPISTID, SENDER, CONTENT, TIMESTAMPSENT, TIMESTAMPREAD, ISDELETED) VALUES
('MAA00001', 'U2500001', 'TP000001', 'USER', 'Hello Dr. Aiman, I need some help with managing stress.', '2025-03-01 08:00:00', NULL, FALSE),
('MAA00002', 'U2500002', 'TP000002', 'USER', 'Hi Nurul, could we discuss some anxiety issues I am facing?', '2025-03-02 09:15:00', '2025-03-02 09:30:00', FALSE),
('MAA00003', 'U2500003', 'TP000003', 'USER', 'Hey Hafiz, I have been struggling with time management, can we talk?', '2025-03-03 10:45:00', NULL, FALSE),
('MAA00004', 'U2500004', 'TP000004', 'USER', 'Hello Chong, I wanted to discuss thoughts on work-life balance.', '2025-03-05 11:00:00', NULL, FALSE),
('MAA00005', 'U2500001', 'TP000005', 'USER', 'Hi Rajesh, can you help me with my self-esteem issues?', '2025-03-06 12:30:00', NULL, FALSE),
('MAA00006', 'U2500006', 'TP000006', 'USER', 'Hi Farah, I need guidance on overcoming my social anxiety.', '2025-03-07 14:00:00', NULL, FALSE),
('MAA00007', 'U2500007', 'TP000007', 'USER', 'Hey Daniel, I would like to talk about some personal struggles I am facing.', '2025-03-08 15:30:00', NULL, FALSE),
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
('APAA0005', 'U2500001', 'TSA00005', 'www.therapymsia.com/appointment/APA0005', 'Scheduled', FALSE),
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


-- REPORTCONTENT Table
INSERT INTO REPORTCONTENT (REPORTCONTENTID, USERID, THREADID, THREADCOMMENTID, REPORTREASON) VALUES 
('RPT00001', 'U2500001', 'TH000010', NULL, 'Disrespectful tone and discourages constructive discussion.'),
('RPT00002', 'U2500001', 'TH000010', 'C0000009', 'Aggressive tone'),
('RPT00003', 'U2500002', 'TH000010', 'C0000010', 'Insulting and disrespectful tone toward other users.');
