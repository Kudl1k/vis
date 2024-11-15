package Mappers;

import DataTransferObjects.UserDTO;
import DomainModels.UserDomainModel;

public class UserDomainMapper extends IDomainMapper<UserDomainModel, UserDTO> {

    @Override
    public UserDomainModel ToDomain(UserDTO dto) {
        UserDomainModel model = new UserDomainModel();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setSurname(dto.getSurname());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setRole(dto.getRole());
        model.setLast_log(dto.getLast_log());
        model.setCreated_at(dto.getCreated_at());
        return model;
    }

    @Override
    public UserDTO ToDTO(UserDomainModel model) {
        return new UserDTO(
                model.getId(),
                model.getName(),
                model.getSurname(),
                model.getEmail(),
                model.getPassword(),
                model.getRole(),
                model.getLast_log(),
                model.getCreated_at()
        );
    }
}
