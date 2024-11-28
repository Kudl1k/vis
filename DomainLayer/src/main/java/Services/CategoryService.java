package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.CategoryDTO;
import DomainModels.CategoryDomainModel;
import Mappers.CategoryDomainMapper;

public class CategoryService {
    private CategoryDomainMapper mapper;

    public CategoryService() {
        this.mapper = new CategoryDomainMapper();
    }

    public CategoryDomainModel[] GetCategories() {
        CategoryDTO[] categories = GlobalConfig
                .connection
                .getCategoryDao()
                .GetCategories();
        CategoryDomainModel[] models = new CategoryDomainModel[categories.length];
        for (int i = 0; i < categories.length; i++) {
            models[i] = mapper.ToDomain(categories[i]);
        }
        return models;
    }

    public CategoryDomainModel GetCategory(String name) {
        CategoryDTO category = GlobalConfig
                .connection
                .getCategoryDao()
                .GetCategory(name);
        return mapper.ToDomain(category);
    }
}
