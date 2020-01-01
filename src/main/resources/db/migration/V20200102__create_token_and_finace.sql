CREATE TABLE token (
  id varchar(50) NOT NULL DEFAULT '',
  user_id varchar(50) DEFAULT NULL,
  last_modified_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE finance (
  id varchar(50) NOT NULL DEFAULT '',
  user_id varchar(50) DEFAULT NULL,
  short_term_debt varchar(50) DEFAULT NULL,
  long_term_debt varchar(50) DEFAULT NULL,
  monthly_debt varchar(50) DEFAULT NULL,
  debt_rate varchar(50) DEFAULT NULL,
  last_modified_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `token_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);