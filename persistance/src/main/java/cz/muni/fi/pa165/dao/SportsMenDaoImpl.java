package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.Role;

public class SportsMenDaoImpl extends UserDaoImpl implements SportsMenDao {
    @Override
    public void create(User entity) {
        entity.setRole(Role.SPORTSMEN);
        super.create(entity);
    }
}
