package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.CategoryDTO;

public interface ICategoryDAO {
    boolean CreateCategory(CategoryDTO category);
    CategoryDTO[] GetCategories();
    CategoryDTO GetCategory(String name);
}
