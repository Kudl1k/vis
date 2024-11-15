package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.CategoryDTO;

public class CategoryTextDataMapper extends IDataMapper<CategoryDTO,String> {
    @Override
    public CategoryDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new CategoryDTO(
                cols[0]
        );
    }

    @Override
    public String ToData(CategoryDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getName());

        return sb.toString();
    }
}
