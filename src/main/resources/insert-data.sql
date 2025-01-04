insert into service_point (id, email, location, name, phone)
values
(1, 'info@gentlemanscut.com', '123 Elm Street, Springfield', 'Gentleman\'s Cut Barbershop', '123-456-7890'),
(2, 'contact@modernblades.com', '456 Oak Avenue, Springfield', 'Modern Blades Barbershop', '987-654-3210'),
(3, 'hello@serenemassage.com', '789 Maple Road, Springfield', 'Serene Massage & Wellness', '555-123-4567'),
(4, 'support@glamourparlour.com', '321 Birch Boulevard, Springfield', 'Glamour Beauty Parlour', '555-987-6543'),
(5, 'contact@harmonyspa.com', '654 Pine Drive, Springfield', 'Harmony Fitness & Spa', '444-222-3333'),
(6, 'bookings@peacefulpose.com', '987 Cedar Lane, Springfield', 'Peaceful Pose Yoga Studio', '666-777-8888'),
(7, 'info@royalnails.com', '111 Walnut Street, Springfield', 'Royal Nail Spa', '111-222-3333'),
(8, 'reservations@skindeepink.com', '222 Chestnut Avenue, Springfield', 'Skin Deep Ink Tattoo Studio', '333-444-5555'),
(9, 'admin@revivemedispa.com', '333 Cypress Way, Springfield', 'Revive Medispa', '777-888-9999'),
(10, 'inquiries@elevatedance.com', '444 Fir Street, Springfield', 'Elevate Dance Academy', '888-999-0000');

insert into employee (id, description, username)
values
(1, 'Senior barber specializing in classic haircuts and shaves', 'JohnDoe'),
(2, 'Experienced tattoo artist skilled in realistic designs', 'JaneSmith89'),
(3, 'Certified yoga instructor with a focus on mindfulness practices', 'MaryJohnson23'),
(4, 'Licensed massage therapist specializing in deep tissue techniques', 'MarkBrown76'),
(5, 'Hairstylist and colorist with 10+ years of salon experience', 'LisaWilliams'),
(6, 'Expert nail technician specializing in intricate nail art', 'EmilyDavis45'),
(7, 'Personal trainer focused on strength and conditioning programs', 'PaulMiller92'),
(8, 'Aesthetician offering skincare and anti-aging treatments', 'KarenWilson'),
(9, 'Dance instructor specializing in hip-hop and modern styles', 'MichaelMoore21'),
(10, 'Tattoo artist with a passion for abstract and tribal designs', 'SamTaylor87'),
(11, 'Barber specializing in fades and modern men\'s styles', 'AlexThomas90'),
(12, 'Massage therapist with expertise in Swedish and aromatherapy massage', 'SaraJackson12'),
(13, 'Yoga teacher focusing on Vinyasa and Hatha yoga flows', 'AnnWhite33'),
(14, 'Fitness coach offering personalized weight loss plans', 'TomHarris54'),
(15, 'Hairstylist known for bridal and event hairdos', 'ClaireMartin'),
(16, 'Nail artist experienced in gel and acrylic enhancements', 'AmyThompson68'),
(17, 'Barber skilled in beard grooming and styling', 'JimGarcia99'),
(18, 'Makeup artist specializing in glam and editorial looks', 'JennyMartinez'),
(19, 'Tattooist specializing in watercolor and minimalist designs', 'LucasRobinson'),
(20, 'Massage therapist certified in sports and injury recovery techniques', 'NinaWalker'),
(21, 'Certified yoga instructor for prenatal and postpartum yoga', 'KateAdams77'),
(22, 'Barber with expertise in classic cuts and vintage styles', 'PeterScott88'),
(23, 'Dance coach for ballroom and Latin dance styles', 'SophieKing56'),
(24, 'Aesthetician offering facials and chemical peels', 'ChloeLewis'),
(25, 'Tattoo artist known for geometric and line art designs', 'RyanCooper43'),
(26, 'Cool barber from Texas', 'JackBaker123'),
(27, 'Massage therapist experienced in reflexology and relaxation techniques', 'JuliaClark'),
(28, 'Barber offering hot towel shaves and premium grooming services', 'SteveYoung19'),
(29, 'Dance teacher focusing on ballet and contemporary styles', 'MeganHill72'),
(30, 'Nail technician with expertise in spa manicures and pedicures', 'SophieWard');

-- Treatments for Gentleman's Cut Barbershop (Service Point ID 1)
insert into treatment (id, description, duration, name, price)
values
(1, 'Classic haircut with a tailored finish', 30, 'Classic Cut', 20.0), -- Also used in Modern Blades
(2, 'Beard trim and shaping with precision tools', 20, 'Beard Trim', 15.0), -- Also used in Modern Blades
(3, 'Hot towel shave with premium products', 40, 'Hot Towel Shave', 25.0), -- Also used in Modern Blades
(4, 'Premium haircut and beard grooming package', 50, 'Cut & Groom', 35.0), -- Unique to Gentleman's Cut
(5, 'Scalp massage and hair treatment', 30, 'Scalp Treatment', 18.0); -- Unique to Gentleman's Cut

-- Treatments for Modern Blades Barbershop (Service Point ID 2)
insert into treatment (id, description, duration, name, price)
values
(6, 'Contemporary haircut with a modern style', 30, 'Modern Cut', 22.0), -- Unique to Modern Blades
(7, 'Clean and precise beard shaping', 15, 'Beard Styling', 12.0), -- Unique to Modern Blades
(8, 'Hot towel shave and skin care', 35, 'Gentleman\'s Shave', 30.0), -- Unique to Modern Blades
(9, 'Haircut and premium styling package', 45, 'Style Package', 40.0), -- Unique to Modern Blades
(10, 'Deep conditioning hair treatment', 25, 'Hair Conditioning', 20.0); -- Unique to Modern Blades

-- Treatments for Serene Massage & Wellness (Service Point ID 3)
insert into treatment (id, description, duration, name, price)
values
(11, 'Relaxing full-body massage', 60, 'Relaxation Massage', 60.0), -- Also used in Harmony Fitness & Spa
(12, 'Deep tissue massage for tension relief', 60, 'Deep Tissue Massage', 70.0), -- Also used in Harmony Fitness & Spa
(13, 'Aromatherapy massage with essential oils', 75, 'Aromatherapy Massage', 80.0), -- Unique to Serene Massage
(14, 'Head, neck, and shoulder massage', 30, 'Quick Stress Relief', 35.0), -- Unique to Serene Massage
(15, 'Hot stone massage for muscle relaxation', 90, 'Hot Stone Massage', 100.0); -- Unique to Serene Massage

-- Treatments for Glamour Beauty Parlour (Service Point ID 4)
insert into treatment (id, description, duration, name, price)
values
(16, 'Haircut and styling for special occasions', 45, 'Event Styling', 50.0), -- Unique to Glamour Parlour
(17, 'Makeup application for weddings or events', 60, 'Bridal Makeup', 75.0), -- Unique to Glamour Parlour
(18, 'Facial for glowing and hydrated skin', 50, 'Hydrating Facial', 40.0), -- Also used in Harmony Fitness & Spa
(19, 'Eyebrow shaping and tinting', 20, 'Brow Shaping', 25.0), -- Unique to Glamour Parlour
(20, 'Manicure with polish application', 30, 'Classic Manicure', 20.0); -- Also used in Royal Nail Spa

-- Treatments for Harmony Fitness & Spa (Service Point ID 5)
insert into treatment (id, description, duration, name, price)
values
(21, 'Access to fitness classes and spa', 90, 'Spa & Fitness Combo', 80.0), -- Unique to Harmony Fitness
(22, 'Relaxing spa package with sauna access', 120, 'Ultimate Spa Day', 120.0), -- Unique to Harmony Fitness
(23, 'Swedish massage for overall relaxation', 60, 'Swedish Massage', 65.0), -- Unique to Harmony Fitness
(24, 'Exfoliating body scrub', 45, 'Body Scrub', 55.0), -- Unique to Harmony Fitness
(25, 'Rejuvenating facial treatment', 50, 'Rejuvenating Facial', 50.0); -- Unique to Harmony Fitness

-- Treatments for Peaceful Pose Yoga Studio (Service Point ID 6)
insert into treatment (id, description, duration, name, price)
values
(26, 'Hatha yoga session', 60, 'Hatha Yoga', 15.0), -- Unique to Peaceful Pose
(27, 'Vinyasa flow yoga session', 60, 'Vinyasa Yoga', 15.0), -- Unique to Peaceful Pose
(28, 'Meditation and mindfulness class', 45, 'Mindfulness Meditation', 10.0), -- Unique to Peaceful Pose
(29, 'Prenatal yoga for expecting mothers', 60, 'Prenatal Yoga', 20.0), -- Unique to Peaceful Pose
(30, 'Restorative yoga for relaxation', 60, 'Restorative Yoga', 15.0); -- Unique to Peaceful Pose

-- Treatments for Royal Nail Spa (Service Point ID 7)
insert into treatment (id, description, duration, name, price)
values
(31, 'Manicure with gel polish application', 45, 'Gel Manicure', 30.0), -- Unique to Royal Nail Spa
(32, 'Deluxe pedicure with foot massage', 60, 'Deluxe Pedicure', 40.0), -- Unique to Royal Nail Spa
(33, 'Acrylic nail extensions with polish', 90, 'Acrylic Extensions', 50.0), -- Unique to Royal Nail Spa
(34, 'Nail art and design application', 30, 'Nail Art', 20.0), -- Unique to Royal Nail Spa
(35, 'Classic manicure with basic polish', 30, 'Basic Manicure', 20.0); -- Also used in Glamour Parlour

-- Treatments for Skin Deep Ink Tattoo Studio (Service Point ID 8)
insert into treatment (id, description, duration, name, price)
values
(36, 'Custom tattoo design consultation', 30, 'Tattoo Consultation', 50.0), -- Unique to Skin Deep Ink
(37, 'Small tattoo with minimal details', 60, 'Small Tattoo', 100.0), -- Unique to Skin Deep Ink
(38, 'Medium-sized tattoo with intricate design', 120, 'Medium Tattoo', 200.0), -- Unique to Skin Deep Ink
(39, 'Large tattoo with complex details', 180, 'Large Tattoo', 400.0), -- Unique to Skin Deep Ink
(40, 'Tattoo touch-up session', 45, 'Tattoo Touch-Up', 75.0); -- Unique to Skin Deep Ink

-- Treatments for Revive Medispa (Service Point ID 9)
insert into treatment (id, description, duration, name, price)
values
(41, 'Botox treatment for wrinkle reduction', 30, 'Botox Treatment', 250.0), -- Unique to Revive Medispa
(42, 'Chemical peel for skin rejuvenation', 45, 'Chemical Peel', 100.0), -- Unique to Revive Medispa
(43, 'Laser hair removal session', 60, 'Laser Hair Removal', 150.0), -- Unique to Revive Medispa
(44, 'Dermal filler application', 45, 'Dermal Fillers', 300.0), -- Unique to Revive Medispa
(45, 'Microneedling for skin renewal', 60, 'Microneedling', 200.0); -- Unique to Revive Medispa

-- Treatments for Elevate Dance Academy (Service Point ID 10)
insert into treatment (id, description, duration, name, price)
values
(46, 'Beginner ballet class', 60, 'Ballet Basics', 20.0), -- Unique to Elevate Dance
(47, 'Hip-hop dance workshop', 90, 'Hip-Hop Workshop', 25.0), -- Unique to Elevate Dance
(48, 'Salsa dance class for couples', 60, 'Salsa Night', 30.0), -- Unique to Elevate Dance
(49, 'Contemporary dance choreography', 75, 'Contemporary Dance', 35.0), -- Unique to Elevate Dance
(50, 'Private dance coaching session', 60, 'Private Coaching', 50.0); -- Unique to Elevate Dance


-- Assigning employees to service points
-- Gentleman's Cut Barbershop (Service Point ID 1)
insert into service_point_employee (employee_id, service_point_id)
values
(1, 1), -- John Doe (Senior barber)
(11, 1), -- Alex Thomas (Barber specializing in fades)
(17, 1), -- Jim Garcia (Beard grooming)
(22, 1), -- Peter Scott (Classic cuts and vintage styles)
(28, 1), -- Steve Young (Hot towel shaves)
(26, 1); -- Jack Baker (Cool barber from Texas)

-- Modern Blades Barbershop (Service Point ID 2)
insert into service_point_employee (employee_id, service_point_id)
values
(1, 2), -- John Doe (Senior barber)
(11, 2), -- Alex Thomas (Barber specializing in fades)
(22, 2), -- Peter Scott (Classic cuts and vintage styles)
(28, 2), -- Steve Young (Hot towel shaves)
(17, 2); -- Jim Garcia (Beard grooming)

-- Serene Massage & Wellness (Service Point ID 3)
insert into service_point_employee (employee_id, service_point_id)
values
(4, 3), -- Mark Brown (Deep tissue massage)
(12, 3), -- Sara Jackson (Swedish massage)
(20, 3), -- Nina Walker (Sports and injury recovery)
(27, 3); -- Julia Clark (Reflexology and relaxation)

-- Glamour Beauty Parlour (Service Point ID 4)
insert into service_point_employee (employee_id, service_point_id)
values
(5, 4), -- Lisa Williams (Hairstylist)
(15, 4), -- Claire Martin (Bridal and event hairdos)
(18, 4), -- Jenny Martinez (Makeup artist)
(24, 4); -- Chloe Lewis (Facials and peels)

-- Harmony Fitness & Spa (Service Point ID 5)
insert into service_point_employee (employee_id, service_point_id)
values
(7, 5), -- Paul Miller (Strength and conditioning trainer)
(14, 5), -- Tom Harris (Weight loss coach)
(20, 5), -- Nina Walker (Sports and injury recovery) -- Also assigned to Serene Massage
(27, 5); -- Julia Clark (Reflexology and relaxation) -- Also assigned to Serene Massage

-- Peaceful Pose Yoga Studio (Service Point ID 6)
insert into service_point_employee (employee_id, service_point_id)
values
(3, 6), -- Mary Johnson (Mindfulness yoga)
(13, 6), -- Ann White (Vinyasa and Hatha yoga)
(21, 6); -- Kate Adams (Prenatal yoga)

-- Royal Nail Spa (Service Point ID 7)
insert into service_point_employee (employee_id, service_point_id)
values
(6, 7), -- Emily Davis (Nail technician)
(16, 7), -- Amy Thompson (Gel and acrylic nails)
(30, 7); -- Sophie Ward (Spa manicures and pedicures)

-- Skin Deep Ink Tattoo Studio (Service Point ID 8)
insert into service_point_employee (employee_id, service_point_id)
values
(2, 8), -- Jane Smith (Realistic designs)
(10, 8), -- Sam Taylor (Abstract and tribal designs)
(19, 8), -- Lucas Robinson (Watercolor and minimalist designs)
(25, 8); -- Ryan Cooper (Geometric designs)

-- Revive Medispa (Service Point ID 9)
insert into service_point_employee (employee_id, service_point_id)
values
(8, 9), -- Karen Wilson (Skincare and anti-aging)
(24, 9); -- Chloe Lewis (Facials and peels) -- Also assigned to Glamour Beauty Parlour

-- Elevate Dance Academy (Service Point ID 10)
insert into service_point_employee (employee_id, service_point_id)
values
(9, 10), -- Michael Moore (Hip-hop instructor)
(23, 10), -- Sophie King (Ballroom and Latin dance)
(29, 10); -- Megan Hill (Ballet and contemporary dance)



-- EMPLOYEE #1 (John Doe) - works at Gentleman's Cut (SP1) & Modern Blades (SP2)
-- Gentleman's Cut has treatments: (1,2,3,4,5)
-- Modern Blades has treatments: (6,7,8,9,10)
-- Assign 4 total
insert into treatment_employees (treatments_id, employees_id)
values
(1, 1),  -- Classic Cut (SP1)
(2, 1),  -- Beard Trim (SP1)
(6, 1),  -- Modern Cut (SP2)
(7, 1);  -- Beard Styling (SP2)


-- EMPLOYEE #2 (Jane Smith) - works at Skin Deep Ink (SP8)
-- SP8 has treatments: (36,37,38,39,40)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(36, 2), -- Tattoo Consultation
(37, 2), -- Small Tattoo
(38, 2), -- Medium Tattoo
(39, 2); -- Large Tattoo


-- EMPLOYEE #3 (Mary Johnson) - Peaceful Pose Yoga (SP6)
-- SP6 has (26,27,28,29,30)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(26, 3), -- Hatha Yoga
(27, 3), -- Vinyasa Yoga
(28, 3), -- Mindfulness Meditation
(29, 3); -- Prenatal Yoga


-- EMPLOYEE #4 (Mark Brown) - Serene Massage & Wellness (SP3)
-- SP3 has (11,12,13,14,15)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(11, 4), -- Relaxation Massage
(12, 4), -- Deep Tissue Massage
(13, 4), -- Aromatherapy Massage
(15, 4); -- Hot Stone Massage


-- EMPLOYEE #5 (Lisa Williams) - Glamour Beauty Parlour (SP4)
-- SP4 has (16,17,18,19,20)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(16, 5), -- Event Styling
(17, 5), -- Bridal Makeup
(18, 5), -- Hydrating Facial
(19, 5); -- Brow Shaping


-- EMPLOYEE #6 (Emily Davis) - Royal Nail Spa (SP7)
-- SP7 has (31,32,33,34,35)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(31, 6), -- Gel Manicure
(32, 6), -- Deluxe Pedicure
(33, 6), -- Acrylic Extensions
(34, 6); -- Nail Art


-- EMPLOYEE #7 (Paul Miller) - Harmony Fitness & Spa (SP5)
-- SP5 has (21,22,23,24,25)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(21, 7), -- Spa & Fitness Combo
(22, 7), -- Ultimate Spa Day
(23, 7), -- Swedish Massage
(24, 7); -- Body Scrub


-- EMPLOYEE #8 (Karen Wilson) - Revive Medispa (SP9)
-- SP9 has (41,42,43,44,45)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(41, 8), -- Botox Treatment
(42, 8), -- Chemical Peel
(44, 8), -- Dermal Fillers
(45, 8); -- Microneedling


-- EMPLOYEE #9 (Michael Moore) - Elevate Dance (SP10)
-- SP10 has (46,47,48,49,50)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(46, 9), -- Ballet Basics
(47, 9), -- Hip-Hop Workshop
(48, 9), -- Salsa Night
(50, 9); -- Private Coaching


-- EMPLOYEE #10 (Sam Taylor) - Skin Deep Ink (SP8)
-- SP8: (36,37,38,39,40)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(36, 10),
(37, 10),
(39, 10),
(40, 10);


-- EMPLOYEE #11 (Alex Thomas) - Gentleman's Cut (SP1) & Modern Blades (SP2)
-- SP1: (1,2,3,4,5), SP2: (6,7,8,9,10)
-- Assign 5 total
insert into treatment_employees (treatments_id, employees_id)
values
(1, 11), -- Classic Cut
(2, 11), -- Beard Trim
(3, 11), -- Hot Towel Shave
(6, 11), -- Modern Cut
(7, 11); -- Beard Styling


-- EMPLOYEE #12 (Sara Jackson) - Serene Massage (SP3)
-- SP3: (11,12,13,14,15)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(11, 12),
(12, 12),
(13, 12),
(15, 12);


-- EMPLOYEE #13 (Ann White) - Peaceful Pose Yoga (SP6)
-- SP6: (26,27,28,29,30)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(26, 13),
(27, 13),
(28, 13),
(30, 13);


-- EMPLOYEE #14 (Tom Harris) - Harmony Fitness & Spa (SP5)
-- SP5: (21,22,23,24,25)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(21, 14),
(22, 14),
(23, 14),
(25, 14);


-- EMPLOYEE #15 (Claire Martin) - Glamour Beauty Parlour (SP4)
-- SP4: (16,17,18,19,20)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(16, 15),
(17, 15),
(18, 15),
(20, 15);


-- EMPLOYEE #16 (Amy Thompson) - Royal Nail Spa (SP7)
-- SP7: (31,32,33,34,35)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(31, 16),
(32, 16),
(33, 16),
(35, 16);


-- EMPLOYEE #17 (Jim Garcia) - Gentleman's Cut (SP1) & Modern Blades (SP2)
-- Assign 5 total
insert into treatment_employees (treatments_id, employees_id)
values
(1, 17),
(3, 17),
(4, 17),
(6, 17),
(8, 17);


-- EMPLOYEE #18 (Jenny Martinez) - Glamour Beauty Parlour (SP4)
-- SP4: (16,17,18,19,20)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(17, 18),
(18, 18),
(19, 18),
(20, 18);


-- EMPLOYEE #19 (Lucas Robinson) - Skin Deep Ink (SP8)
-- SP8: (36,37,38,39,40)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(37, 19),
(38, 19),
(39, 19),
(40, 19);


-- EMPLOYEE #20 (Nina Walker) - Serene Massage (SP3) & Harmony Fitness (SP5)
-- SP3: (11,12,13,14,15), SP5: (21,22,23,24,25)
-- Assign 6 total
insert into treatment_employees (treatments_id, employees_id)
values
(11, 20),
(12, 20),
(14, 20),
(23, 20),
(22, 20),
(25, 20);


-- EMPLOYEE #21 (Kate Adams) - Peaceful Pose Yoga (SP6)
-- SP6: (26,27,28,29,30)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(26, 21),
(28, 21),
(29, 21),
(30, 21);


-- EMPLOYEE #22 (Peter Scott) - Gentleman's Cut (SP1) & Modern Blades (SP2)
-- Assign 5 total
insert into treatment_employees (treatments_id, employees_id)
values
(1, 22),
(2, 22),
(4, 22),
(6, 22),
(7, 22);


-- EMPLOYEE #23 (Sophie King) - Elevate Dance (SP10)
-- SP10: (46,47,48,49,50)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(46, 23),
(47, 23),
(49, 23),
(50, 23);


-- EMPLOYEE #24 (Chloe Lewis) - Glamour Beauty Parlour (SP4) & Revive Medispa (SP9)
-- SP4: (16,17,18,19,20), SP9: (41,42,43,44,45)
-- Assign 6 total
insert into treatment_employees (treatments_id, employees_id)
values
(16, 24),
(18, 24),
(19, 24),
(41, 24),
(42, 24),
(45, 24);


-- EMPLOYEE #25 (Ryan Cooper) - Skin Deep Ink (SP8)
-- SP8: (36,37,38,39,40)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(36, 25),
(38, 25),
(39, 25),
(40, 25);


-- EMPLOYEE #26 (Jack Baker) - Gentleman's Cut (SP1)
-- SP1: (1,2,3,4,5)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(1, 26),
(2, 26),
(3, 26),
(4, 26);


-- EMPLOYEE #27 (Julia Clark) - Serene Massage (SP3) & Harmony Fitness (SP5)
-- Assign 6 total
insert into treatment_employees (treatments_id, employees_id)
values
(12, 27), -- Deep Tissue Massage (SP3)
(13, 27), -- Aromatherapy (SP3)
(15, 27), -- Hot Stone (SP3)
(21, 27), -- Spa & Fitness (SP5)
(24, 27), -- Body Scrub (SP5)
(25, 27); -- Rejuvenating Facial (SP5)


-- EMPLOYEE #28 (Steve Young) - Gentleman's Cut (SP1) & Modern Blades (SP2)
-- Assign 5 total
insert into treatment_employees (treatments_id, employees_id)
values
(3, 28),
(4, 28),
(8, 28),
(9, 28),
(10, 28);


-- EMPLOYEE #29 (Megan Hill) - Elevate Dance (SP10)
-- SP10: (46,47,48,49,50)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(46, 29),
(48, 29),
(49, 29),
(50, 29);


-- EMPLOYEE #30 (Sophie Ward) - Royal Nail Spa (SP7)
-- SP7: (31,32,33,34,35)
-- Assign 4
insert into treatment_employees (treatments_id, employees_id)
values
(31, 30),
(32, 30),
(34, 30),
(35, 30);


-- SERVICE POINT #1: Gentleman's Cut Barbershop
-- Employees here: #1, #11, #17, #22, #26, #28
-- Treatments at SP1: #1 (Classic Cut), #2 (Beard Trim), #3 (Hot Towel Shave), #4 (Cut & Groom), #5 (Scalp Treatment)

-- 5 Bookings for Service Point #1
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #1
(1, '2023-07-01 10:00:00', 1, 1, 1, 1),   -- John Doe does "Classic Cut" for user #1
-- Booking #2
(2, '2023-07-02 09:30:00', 11, 1, 2, 2),  -- Alex Thomas does "Beard Trim" for user #2
-- Booking #3
(3, '2023-07-03 11:00:00', 17, 1, 3, 3),  -- Jim Garcia does "Hot Towel Shave" for user #3
-- Booking #4
(4, '2023-07-03 14:00:00', 22, 1, 4, 4),  -- Peter Scott does "Cut & Groom" for user #4
-- Booking #5
(5, '2023-07-04 10:30:00', 26, 1, 5, 5);  -- Jack Baker does "Scalp Treatment" for user #5

-- SERVICE POINT #2: Modern Blades Barbershop
-- Employees here: #1, #11, #17, #22, #28
-- Treatments at SP2: #6 (Modern Cut), #7 (Beard Styling), #8 (Gentleman's Shave),
--                    #9 (Style Package), #10 (Hair Conditioning)

-- 5 Bookings for Service Point #2
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #6
(6, '2023-07-05 09:00:00', 1, 2, 6, 6),   -- John Doe does "Modern Cut" for user #6
-- Booking #7
(7, '2023-07-06 10:30:00', 28, 2, 7, 1),  -- Steve Young does "Beard Styling" for user #1
-- Booking #8
(8, '2023-07-07 10:00:00', 17, 2, 8, 2),  -- Jim Garcia does "Gentleman's Shave" for user #2
-- Booking #9
(9, '2023-07-08 14:15:00', 22, 2, 9, 3),  -- Peter Scott does "Style Package" for user #3
-- Booking #10
(10, '2023-07-09 12:00:00', 11, 2, 10, 4); -- Alex Thomas does "Hair Conditioning" for user #4

-- SERVICE POINT #3: Serene Massage & Wellness
-- Employees here: #4, #12, #20, #27
-- Treatments at SP3: #11 (Relaxation Massage), #12 (Deep Tissue), #13 (Aromatherapy),
--                    #14 (Quick Stress Relief), #15 (Hot Stone Massage)

-- 5 Bookings for Service Point #3
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #11
(11, '2023-07-10 09:00:00', 4, 3, 11, 5),  -- Mark Brown does "Relaxation Massage"
-- Booking #12
(12, '2023-07-11 10:00:00', 12, 3, 12, 6), -- Sara Jackson does "Deep Tissue Massage"
-- Booking #13
(13, '2023-07-12 13:30:00', 20, 3, 13, 1), -- Nina Walker does "Aromatherapy Massage"
-- Booking #14
(14, '2023-07-13 11:15:00', 27, 3, 14, 2), -- Julia Clark does "Quick Stress Relief"
-- Booking #15
(15, '2023-07-14 15:00:00', 4, 3, 15, 3);  -- Mark Brown does "Hot Stone Massage"

-- SERVICE POINT #4: Glamour Beauty Parlour
-- Employees here: #5, #15, #18, #24
-- Treatments at SP4: #16 (Event Styling), #17 (Bridal Makeup), #18 (Hydrating Facial),
--                    #19 (Brow Shaping), #20 (Classic Manicure)

-- 5 Bookings for Service Point #4
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #16
(16, '2023-07-15 10:00:00', 5, 4, 16, 4),   -- Lisa Williams "Event Styling"
-- Booking #17
(17, '2023-07-16 11:45:00', 15, 4, 17, 5),  -- Claire Martin "Bridal Makeup"
-- Booking #18
(18, '2023-07-17 09:30:00', 18, 4, 18, 6),  -- Jenny Martinez "Hydrating Facial"
-- Booking #19
(19, '2023-07-18 14:00:00', 24, 4, 19, 1),  -- Chloe Lewis "Brow Shaping"
-- Booking #20
(20, '2023-07-19 16:30:00', 5, 4, 20, 2);   -- Lisa Williams "Classic Manicure"

-- SERVICE POINT #5: Harmony Fitness & Spa
-- Employees here: #7, #14, #20, #27
-- Treatments at SP5: #21 (Spa & Fitness), #22 (Ultimate Spa), #23 (Swedish Massage),
--                    #24 (Body Scrub), #25 (Rejuvenating Facial)

-- 5 Bookings for Service Point #5
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #21
(21, '2023-07-20 09:15:00', 7, 5, 21, 3),   -- Paul Miller "Spa & Fitness Combo"
-- Booking #22
(22, '2023-07-21 10:15:00', 14, 5, 22, 4),  -- Tom Harris "Ultimate Spa Day"
-- Booking #23
(23, '2023-07-22 11:30:00', 20, 5, 23, 5),  -- Nina Walker "Swedish Massage"
-- Booking #24
(24, '2023-07-23 13:00:00', 27, 5, 24, 6),  -- Julia Clark "Body Scrub"
-- Booking #25
(25, '2023-07-24 14:00:00', 14, 5, 25, 1);  -- Tom Harris "Rejuvenating Facial"

-- SERVICE POINT #6: Peaceful Pose Yoga Studio
-- Employees here: #3, #13, #21
-- Treatments at SP6: #26 (Hatha Yoga), #27 (Vinyasa Yoga), #28 (Mindfulness),
--                    #29 (Prenatal Yoga), #30 (Restorative Yoga)

-- 5 Bookings for Service Point #6
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #26
(26, '2023-08-01 08:00:00', 3, 6, 26, 2),   -- Mary Johnson "Hatha Yoga"
-- Booking #27
(27, '2023-08-02 09:00:00', 13, 6, 27, 3),  -- Ann White "Vinyasa Yoga"
-- Booking #28
(28, '2023-08-03 10:00:00', 21, 6, 29, 4),  -- Kate Adams "Prenatal Yoga"
-- Booking #29
(29, '2023-08-04 11:30:00', 3, 6, 28, 5),   -- Mary Johnson "Mindfulness"
-- Booking #30
(30, '2023-08-05 12:45:00', 21, 6, 30, 6);  -- Kate Adams "Restorative Yoga"

-- SERVICE POINT #7: Royal Nail Spa
-- Employees here: #6, #16, #30
-- Treatments at SP7: #31, #32, #33, #34, #35

-- 5 Bookings for Service Point #7
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #31
(31, '2023-08-06 09:00:00', 6, 7, 31, 1),  -- Emily Davis "Gel Manicure"
-- Booking #32
(32, '2023-08-07 09:45:00', 16, 7, 33, 2), -- Amy Thompson "Acrylic Extensions"
-- Booking #33
(33, '2023-08-08 10:30:00', 30, 7, 34, 3), -- Sophie Ward "Nail Art"
-- Booking #34
(34, '2023-08-09 11:15:00', 6, 7, 32, 4),  -- Emily Davis "Deluxe Pedicure"
-- Booking #35
(35, '2023-08-10 13:00:00', 16, 7, 35, 5); -- Amy Thompson "Basic Manicure"

-- SERVICE POINT #8: Skin Deep Ink Tattoo Studio
-- Employees here: #2, #10, #19, #25
-- Treatments at SP8: #36, #37, #38, #39, #40

-- 5 Bookings for Service Point #8
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #36
(36, '2023-08-11 09:30:00', 2, 8, 36, 6),  -- Jane Smith "Tattoo Consultation"
-- Booking #37
(37, '2023-08-12 10:00:00', 10, 8, 37, 1), -- Sam Taylor "Small Tattoo"
-- Booking #38
(38, '2023-08-13 11:30:00', 19, 8, 39, 2), -- Lucas Robinson "Large Tattoo"
-- Booking #39
(39, '2023-08-14 14:00:00', 25, 8, 38, 3), -- Ryan Cooper "Medium Tattoo"
-- Booking #40
(40, '2023-08-15 09:45:00', 19, 8, 40, 4); -- Lucas Robinson "Tattoo Touch-Up"

-- SERVICE POINT #9: Revive Medispa
-- Employees here: #8, #24
-- Treatments at SP9: #41, #42, #43, #44, #45

-- 5 Bookings for Service Point #9
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #41
(41, '2023-08-16 08:30:00', 8, 9, 41, 5),  -- Karen Wilson "Botox Treatment"
-- Booking #42
(42, '2023-08-17 09:15:00', 24, 9, 42, 6), -- Chloe Lewis "Chemical Peel"
-- Booking #43
(43, '2023-08-18 10:00:00', 8, 9, 44, 1),  -- Karen Wilson "Dermal Fillers"
-- Booking #44
(44, '2023-08-19 11:45:00', 24, 9, 45, 2), -- Chloe Lewis "Microneedling"
-- Booking #45
(45, '2023-08-20 13:00:00', 24, 9, 18, 3); -- Chloe Lewis also does "Hydrating Facial" from SP4, but we assume it's offered at SP9 for demonstration
-- (If you prefer strict rules, skip cross-service treatments.)

-- SERVICE POINT #10: Elevate Dance Academy
-- Employees here: #9, #23, #29
-- Treatments at SP10: #46, #47, #48, #49, #50

-- 5 Bookings for Service Point #10
insert into booking (id, date, employee_id, service_point_id, treatment_id, user_id)
values
-- Booking #46
(46, '2023-08-21 09:00:00', 9, 10, 46, 4),   -- Michael Moore "Ballet Basics"
-- Booking #47
(47, '2023-08-22 09:45:00', 23, 10, 47, 5),  -- Sophie King "Hip-Hop Workshop"
-- Booking #48
(48, '2023-08-23 10:30:00', 29, 10, 48, 6),  -- Megan Hill "Salsa Night"
-- Booking #49
(49, '2023-08-24 11:15:00', 23, 10, 49, 1),  -- Sophie King "Contemporary Dance"
-- Booking #50
(50, '2023-08-25 12:45:00', 9, 10, 50, 2);   -- Michael Moore "Private Coaching"

-- All reviews correspond to booking IDs 1 through 50
-- 'date' is set 1–3 days after each booking's date, rating is 1–5, text is sample feedback.

insert into review (id, date, rating, text, booking_id)
values
(1,  '2023-07-03', 5, 'Fantastic service, highly recommended.', 1),
(2,  '2023-07-04', 4, 'Good experience, will come again.',       2),
(3,  '2023-07-05', 5, 'Superb attention to detail!',             3),
(4,  '2023-07-05', 5, 'Really enjoyed the overall styling.',      4),
(5,  '2023-07-06', 5, 'Best scalp treatment I\'ve ever had.',     5),

(6,  '2023-07-07', 4, 'Clean cut, modern style as promised.',     6),
(7,  '2023-07-08', 5, 'Loved the beard styling technique.',       7),
(8,  '2023-07-09', 3, 'Shave was okay, a bit rushed.',            8),
(9,  '2023-07-10', 4, 'Impressive styling package, great vibe.',  9),
(10, '2023-07-11', 2, 'Conditioning was fine, but slow service.', 10),

(11, '2023-07-12', 5, 'Truly relaxing massage session.',          11),
(12, '2023-07-13', 4, 'Deep tissue helped relieve tension.',      12),
(13, '2023-07-15', 5, 'Aromatherapy was heavenly.',               13),
(14, '2023-07-15', 2, 'Neck massage was too short.',              14),
(15, '2023-07-16', 4, 'Loved the hot stone technique.',           15),

(16, '2023-07-17', 5, 'Event styling was picture-perfect.',       16),
(17, '2023-07-18', 3, 'Good bridal makeup but ran a bit late.',   17),
(18, '2023-07-19', 5, 'Facial left my skin glowing!',             18),
(19, '2023-07-20', 4, 'Brow shaping was on point.',               19),
(20, '2023-07-21', 4, 'Manicure was neat and quick.',             20),

(21, '2023-07-22', 3, 'Decent spa & fitness combo.',              21),
(22, '2023-07-23', 5, 'Ultimate spa day = ultimate relaxation!',  22),
(23, '2023-07-24', 1, 'Swedish massage was too light for me.',    23),
(24, '2023-07-25', 2, 'Body scrub was okay, not memorable.',      24),
(25, '2023-07-26', 5, 'Facial treatment was fantastic!',          25),

(26, '2023-08-02', 3, 'Hatha yoga was relaxing overall.',         26),
(27, '2023-08-03', 4, 'Vinyasa flow class was energetic!',        27),
(28, '2023-08-05', 5, 'Mindfulness session changed my mindset.',  28),
(29, '2023-08-06', 3, 'Prenatal yoga was helpful but crowded.',   29),
(30, '2023-08-07', 4, 'Restorative yoga melted my stress away.',  30),

(31, '2023-08-08', 5, 'Gel manicure looks stunning!',             31),
(32, '2023-08-09', 5, 'Acrylic extensions done flawlessly.',      32),
(33, '2023-08-10', 2, 'Nail art design was subpar.',              33),
(34, '2023-08-10', 3, 'Pedicure was decent, a bit pricey.',       34),
(35, '2023-08-11', 4, 'Basic manicure was quick & clean.',        35),

(36, '2023-08-13', 5, 'Consultation was thorough, awesome ideas.',36),
(37, '2023-08-14', 1, 'Small tattoo turned out crooked.',         37),
(38, '2023-08-15', 5, 'Large tattoo was epic, super detailed!',   38),
(39, '2023-08-16', 2, 'Medium tattoo took too long.',             39),
(40, '2023-08-17', 4, 'Touch-up session fixed fading issue.',     40),

(41, '2023-08-18', 5, 'Botox treatment was very professional.',   41),
(42, '2023-08-19', 5, 'Chemical peel gave me fresh skin.',         42),
(43, '2023-08-20', 3, 'Laser hair removal was decent, mild pain.',43),
(44, '2023-08-21', 4, 'Dermal fillers look natural.',             44),
(45, '2023-08-22', 1, 'Microneedling was too uncomfortable.',     45),

(46, '2023-08-23', 5, 'Ballet basics were well taught.',          46),
(47, '2023-08-24', 2, 'Hip-hop workshop was too advanced.',       47),
(48, '2023-08-25', 4, 'Salsa night was fun and lively.',          48),
(49, '2023-08-26', 5, 'Contemporary dance was inspiring.',        49),
(50, '2023-08-27', 3, 'Private coaching was good, but expensive.',50);



-- Employee 1 (JohnDoe)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(1,  'MONDAY',    '14:00:00', '09:00:00', 1),
(2,  'WEDNESDAY', '18:00:00', '10:00:00', 1),
-- Split day FRIDAY
(3,  'FRIDAY',    '14:00:00', '09:00:00', 1),
(4,  'FRIDAY',    '20:00:00', '14:00:00', 1);

-- Employee 2 (JaneSmith89)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(5,  'TUESDAY',   '13:00:00', '08:00:00', 2),
(6,  'THURSDAY',  '17:00:00', '12:00:00', 2),
-- Split day SATURDAY
(7,  'SATURDAY',  '14:00:00', '09:00:00', 2),
(8,  'SATURDAY',  '19:00:00', '14:00:00', 2);

-- Employee 3 (MaryJohnson23)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(9,  'MONDAY',    '15:00:00', '10:00:00', 3),
(10, 'WEDNESDAY', '18:00:00', '13:00:00', 3),
-- Split day FRIDAY
(11, 'FRIDAY',    '12:00:00', '07:00:00', 3),
(12, 'FRIDAY',    '17:00:00', '12:00:00', 3);

-- Employee 4 (MarkBrown76)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(13, 'TUESDAY',   '14:00:00', '09:00:00', 4),
(14, 'THURSDAY',  '18:00:00', '12:00:00', 4),
-- Split day SATURDAY
(15, 'SATURDAY',  '15:00:00', '09:00:00', 4),
(16, 'SATURDAY',  '20:00:00', '15:00:00', 4);

-- Employee 5 (LisaWilliams)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(17, 'MONDAY',    '16:00:00', '10:00:00', 5),
(18, 'WEDNESDAY', '19:00:00', '14:00:00', 5),
-- Split day FRIDAY
(19, 'FRIDAY',    '13:00:00', '08:00:00', 5),
(20, 'FRIDAY',    '18:00:00', '13:00:00', 5);

-- Employee 6 (EmilyDavis45)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(21, 'TUESDAY',   '14:00:00', '09:00:00', 6),
(22, 'THURSDAY',  '20:00:00', '15:00:00', 6),
-- Split day SATURDAY
(23, 'SATURDAY',  '13:00:00', '08:00:00', 6),
(24, 'SATURDAY',  '18:00:00', '13:00:00', 6);

-- Employee 7 (PaulMiller92)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(25, 'MONDAY',    '15:00:00', '09:00:00', 7),
(26, 'WEDNESDAY', '18:00:00', '12:00:00', 7),
-- Split day FRIDAY
(27, 'FRIDAY',    '14:00:00', '09:00:00', 7),
(28, 'FRIDAY',    '19:00:00', '14:00:00', 7);

-- Employee 8 (KarenWilson)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(29, 'TUESDAY',   '13:00:00', '08:00:00', 8),
(30, 'THURSDAY',  '17:00:00', '12:00:00', 8),
-- Split day SATURDAY
(31, 'SATURDAY',  '14:00:00', '09:00:00', 8),
(32, 'SATURDAY',  '19:00:00', '14:00:00', 8);

-- Employee 9 (MichaelMoore21)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(33, 'MONDAY',    '15:00:00', '10:00:00', 9),
(34, 'WEDNESDAY', '20:00:00', '15:00:00', 9),
-- Split day FRIDAY
(35, 'FRIDAY',    '12:00:00', '07:00:00', 9),
(36, 'FRIDAY',    '17:00:00', '12:00:00', 9);

-- Employee 10 (SamTaylor87)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(37, 'TUESDAY',   '16:00:00', '10:00:00', 10),
(38, 'THURSDAY',  '19:00:00', '13:00:00', 10),
-- Split day SATURDAY
(39, 'SATURDAY',  '14:00:00', '09:00:00', 10),
(40, 'SATURDAY',  '19:00:00', '14:00:00', 10);

-- Employee 11 (AlexThomas90)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(41, 'MONDAY',    '14:00:00', '09:00:00', 11),
(42, 'WEDNESDAY', '18:00:00', '12:00:00', 11),
-- Split day FRIDAY
(43, 'FRIDAY',    '13:00:00', '08:00:00', 11),
(44, 'FRIDAY',    '18:00:00', '13:00:00', 11);

-- Employee 12 (SaraJackson12)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(45, 'TUESDAY',   '15:00:00', '09:00:00', 12),
(46, 'THURSDAY',  '20:00:00', '14:00:00', 12),
-- Split day SATURDAY
(47, 'SATURDAY',  '13:00:00', '08:00:00', 12),
(48, 'SATURDAY',  '18:00:00', '13:00:00', 12);

-- Employee 13 (AnnWhite33)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(49, 'MONDAY',    '15:00:00', '10:00:00', 13),
(50, 'WEDNESDAY', '19:00:00', '14:00:00', 13),
-- Split day FRIDAY
(51, 'FRIDAY',    '12:00:00', '07:00:00', 13),
(52, 'FRIDAY',    '17:00:00', '12:00:00', 13);

-- Employee 14 (TomHarris54)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(53, 'TUESDAY',   '14:00:00', '09:00:00', 14),
(54, 'THURSDAY',  '18:00:00', '12:00:00', 14),
-- Split day SATURDAY
(55, 'SATURDAY',  '14:00:00', '09:00:00', 14),
(56, 'SATURDAY',  '19:00:00', '14:00:00', 14);

-- Employee 15 (ClaireMartin)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(57, 'MONDAY',    '14:00:00', '09:00:00', 15),
(58, 'WEDNESDAY', '18:00:00', '12:00:00', 15),
-- Split day FRIDAY
(59, 'FRIDAY',    '13:00:00', '08:00:00', 15),
(60, 'FRIDAY',    '18:00:00', '13:00:00', 15);

-- Employee 16 (AmyThompson68)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(61, 'TUESDAY',   '13:00:00', '08:00:00', 16),
(62, 'THURSDAY',  '17:00:00', '12:00:00', 16),
-- Split day SATURDAY
(63, 'SATURDAY',  '14:00:00', '09:00:00', 16),
(64, 'SATURDAY',  '19:00:00', '14:00:00', 16);

-- Employee 17 (JimGarcia99)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(65, 'MONDAY',    '14:00:00', '09:00:00', 17),
(66, 'WEDNESDAY', '19:00:00', '14:00:00', 17),
-- Split day FRIDAY
(67, 'FRIDAY',    '13:00:00', '08:00:00', 17),
(68, 'FRIDAY',    '18:00:00', '13:00:00', 17);

-- Employee 18 (JennyMartinez)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(69, 'TUESDAY',   '15:00:00', '09:00:00', 18),
(70, 'THURSDAY',  '19:00:00', '13:00:00', 18),
-- Split day SATURDAY
(71, 'SATURDAY',  '12:00:00', '07:00:00', 18),
(72, 'SATURDAY',  '17:00:00', '12:00:00', 18);

-- Employee 19 (LucasRobinson)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(73, 'MONDAY',    '16:00:00', '10:00:00', 19),
(74, 'WEDNESDAY', '20:00:00', '14:00:00', 19),
-- Split day FRIDAY
(75, 'FRIDAY',    '14:00:00', '09:00:00', 19),
(76, 'FRIDAY',    '19:00:00', '14:00:00', 19);

-- Employee 20 (NinaWalker)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(77, 'TUESDAY',   '14:00:00', '09:00:00', 20),
(78, 'THURSDAY',  '20:00:00', '15:00:00', 20),
-- Split day SATURDAY
(79, 'SATURDAY',  '13:00:00', '08:00:00', 20),
(80, 'SATURDAY',  '18:00:00', '13:00:00', 20);

-- Employee 21 (KateAdams77)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(81, 'MONDAY',    '14:00:00', '09:00:00', 21),
(82, 'WEDNESDAY', '19:00:00', '13:00:00', 21),
-- Split day FRIDAY
(83, 'FRIDAY',    '12:00:00', '07:00:00', 21),
(84, 'FRIDAY',    '17:00:00', '12:00:00', 21);

-- Employee 22 (PeterScott88)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(85, 'TUESDAY',   '16:00:00', '10:00:00', 22),
(86, 'THURSDAY',  '19:00:00', '13:00:00', 22),
-- Split day SATURDAY
(87, 'SATURDAY',  '14:00:00', '09:00:00', 22),
(88, 'SATURDAY',  '20:00:00', '14:00:00', 22);

-- Employee 23 (SophieKing56)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(89, 'MONDAY',    '15:00:00', '10:00:00', 23),
(90, 'WEDNESDAY', '20:00:00', '15:00:00', 23),
-- Split day FRIDAY
(91, 'FRIDAY',    '13:00:00', '08:00:00', 23),
(92, 'FRIDAY',    '18:00:00', '13:00:00', 23);

-- Employee 24 (ChloeLewis)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(93, 'TUESDAY',   '13:00:00', '08:00:00', 24),
(94, 'THURSDAY',  '17:00:00', '12:00:00', 24),
-- Split day SATURDAY
(95, 'SATURDAY',  '14:00:00', '09:00:00', 24),
(96, 'SATURDAY',  '19:00:00', '14:00:00', 24);

-- Employee 25 (RyanCooper43)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(97,  'MONDAY',    '14:00:00', '09:00:00', 25),
(98,  'WEDNESDAY', '19:00:00', '13:00:00', 25),
-- Split day FRIDAY
(99,  'FRIDAY',    '13:00:00', '08:00:00', 25),
(100, 'FRIDAY',    '18:00:00', '13:00:00', 25);

-- Employee 26 (JackBaker123)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(101, 'TUESDAY',   '14:00:00', '09:00:00', 26),
(102, 'THURSDAY',  '20:00:00', '15:00:00', 26),
-- Split day SATURDAY
(103, 'SATURDAY',  '13:00:00', '08:00:00', 26),
(104, 'SATURDAY',  '18:00:00', '13:00:00', 26);

-- Employee 27 (JuliaClark)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(105, 'MONDAY',    '14:00:00', '09:00:00', 27),
(106, 'WEDNESDAY', '18:00:00', '12:00:00', 27),
-- Split day FRIDAY
(107, 'FRIDAY',    '13:00:00', '08:00:00', 27),
(108, 'FRIDAY',    '18:00:00', '13:00:00', 27);

-- Employee 28 (SteveYoung19)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(109, 'TUESDAY',   '13:00:00', '08:00:00', 28),
(110, 'THURSDAY',  '17:00:00', '12:00:00', 28),
-- Split day SATURDAY
(111, 'SATURDAY',  '14:00:00', '09:00:00', 28),
(112, 'SATURDAY',  '19:00:00', '14:00:00', 28);

-- Employee 29 (MeganHill72)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(113, 'MONDAY',    '16:00:00', '10:00:00', 29),
(114, 'WEDNESDAY', '20:00:00', '14:00:00', 29),
-- Split day FRIDAY
(115, 'FRIDAY',    '14:00:00', '09:00:00', 29),
(116, 'FRIDAY',    '20:00:00', '14:00:00', 29);

-- Employee 30 (SophieWard)
insert into employee_availability (id, dayOfWeek, endTime, startTime, employee_id) values
(117, 'TUESDAY',   '13:00:00', '08:00:00', 30),
(118, 'THURSDAY',  '17:00:00', '12:00:00', 30),
-- Split day SATURDAY
(119, 'SATURDAY',  '14:00:00', '09:00:00', 30),
(120, 'SATURDAY',  '20:00:00', '14:00:00', 30);
