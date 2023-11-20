INSERT INTO ROLES (id, `name`) VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_APP');

INSERT INTO USERS (`username`,`password`, `name`) VALUES
    ('app_admin','$2a$10$2p5qhDyih2TUnzP28Di/a.ln7.fwOmOLx4rmXP4IONIWs.c18G5uq','Application Admin'),
    ('user1','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Anderson'),
    ('user2','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Begins'),
    ('user3','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Copper'),
    ('user4','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Doe'),
    ('user5','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Evens'),
    ('user6','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Fox'),
    ('user7','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Gigs'),
    ('user8','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Hook'),
    ('user9','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Ians'),
    ('user10','$2a$10$MbQbawzkSexrzmy.NiAKJ.3r1e49zPBQwemmVHvOvahg568JPCQ6K','John Jones');

INSERT INTO USER_ROLES (`user_id`,`role_id`) VALUES
    (1,2),
    (2,1),
    (3,1),
    (4,1),
    (5,1),
    (6,1),
    (7,1),
    (8,1),
    (9,1),
    (10,1),
    (11,1);