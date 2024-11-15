package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.ICategoryDAO;
import DataAccess.DataAccessObjects.Mappers.CategoryTextDataMapper;
import DataTransferObjects.CategoryDTO;

import java.nio.file.Path;
import java.util.ArrayList;

public class CategoryTextDAO implements ICategoryDAO {
    public static String categoryFile = "Categories.csv";

    private CategoryTextDataMapper categoryMapper;

    public CategoryTextDAO() {
        categoryMapper = new CategoryTextDataMapper();
    }

    @Override
    public boolean CreateCategory(CategoryDTO category) {
        Path path = TextConnectorUtils.fullFilePath(categoryFile);
        System.out.println(path.toString());
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<CategoryDTO> categories = categoryMapper.ToDTOList(lines);

        if (categories.stream().anyMatch(c -> c.getName().equals(category.getName()))) {
            return false;
        }

        categories.add(category);

        TextConnectorUtils.saveToFile(categoryMapper.ToDataList(categories), categoryFile);

        return true;
    }

    @Override
    public CategoryDTO[] GetCategories() {
        Path path = TextConnectorUtils.fullFilePath(categoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<CategoryDTO> categories = categoryMapper.ToDTOList(lines);

        return categories.toArray(new CategoryDTO[0]);
    }

    @Override
    public CategoryDTO GetCategory(String name) {
        Path path = TextConnectorUtils.fullFilePath(categoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<CategoryDTO> categories = categoryMapper.ToDTOList(lines);

        return categories.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }
}
