INSERT INTO ROLES (id, `name`) VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

INSERT INTO USERS (`id`, `username`,`password`, `name`) VALUES
    (1, 'user1','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Anderson'),
    (2, 'user2','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Begins'),
    (3, 'user3','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Copper'),
    (4, 'user4','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Doe'),
    (5, 'user5','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Evens'),
    (6 ,'user6','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Fox'),
    (7, 'user7','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Gigs'),
    (8, 'user8','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Hook'),
    (9, 'user9','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Ians'),
    (10, 'user10','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Jones');

INSERT INTO USER_ROLES (`user_id`,`role_id`) VALUES
    (1,1),
    (2,1),
    (3,1),
    (4,1),
    (5,1),
    (6,1),
    (7,1),
    (8,1),
    (9,1),
    (10,1);