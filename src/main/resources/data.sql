INSERT INTO user_role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO user_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

INSERT INTO clients VALUES (1,'$2a$04$PSGWzQp/5wvXLGOVRj.y5e9xAGSP0oSvSSQQ1UtazJlw2UbefFd1O','user');
INSERT INTO clients VALUES (2,'$2a$04$Gy5UF4iNARNIf.lDoBl5heTOnYuODrhZlk6DHAs70SEpHr5Y1AsRC','admin');

INSERT INTO app_role(user_id, role_id) VALUES(1,1);
INSERT INTO app_role(user_id, role_id) VALUES(2,1);
INSERT INTO app_role(user_id, role_id) VALUES(2,2);

