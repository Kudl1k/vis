package Mappers;

import DataTransferObjects.CategoryDTO;
import DomainModels.CategoryDomainModel;

public class CategoryDomainMapper extends IDomainMapper<CategoryDomainModel, CategoryDTO> {
    @Override
    public CategoryDomainModel ToDomain(CategoryDTO data) {
        return new CategoryDomainModel(data.getName());
    }

    @Override
    public CategoryDTO ToDTO(CategoryDomainModel data) {
        return new CategoryDTO(data.getName());
    }
}
