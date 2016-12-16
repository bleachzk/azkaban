CREATE TABLE group_info (
  id int(16) NOT NULL AUTO_INCREMENT,
  groupname varchar(64) NOT NULL COMMENT '组名',
  roleid int(16) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (id,groupname)
) ENGINE=InnoDB AUTO_INCREMENT DEFAULT CHARSET=utf8;