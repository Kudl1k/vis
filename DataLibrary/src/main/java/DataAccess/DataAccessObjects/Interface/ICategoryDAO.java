package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.CategoryDTO;

public interface ICategoryDAO {
    boolean CreateCategory(String name);
    CategoryDTO[] GetCategories();
    CategoryDTO GetCategory(int id);
}
