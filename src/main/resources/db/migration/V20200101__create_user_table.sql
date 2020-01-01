CREATE TABLE user (
  id varchar(50) NOT NULL DEFAULT '',
  username varchar(50) NOT NULL,
  password varchar(500) NOT NULL,
  last_modified_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY username (username)
);
