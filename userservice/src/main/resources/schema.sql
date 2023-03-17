drop TABLE if exists user;
drop TABLE if exists User;
CREATE TABLE user
(
   user_id int (11) NOT NULL AUTO_INCREMENT,
   lastName varchar (45) NOT NULL,
   firstName varchar (45) NOT NULL,
   dateOfBirth Date NOT NULL,
   genre varchar (1) NOT NULL,
   postalAddress varchar (255),
   telephone varchar (20),
   PRIMARY KEY (`user_id`)
);