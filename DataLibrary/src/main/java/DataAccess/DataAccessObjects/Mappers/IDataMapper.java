package DataAccess.DataAccessObjects.Mappers;

import java.util.ArrayList;

public abstract class IDataMapper<T,U> {
    public abstract T ToDTO(U data);
    public abstract U ToData(T data);

    public ArrayList<T> ToDTOList(Iterable<U> data){
        ArrayList<T> dtos = new ArrayList<T>();
        for(U line: data) {
            dtos.add(ToDTO(line));
        }
        return dtos;
    }

    public ArrayList<U> ToDataList(Iterable<T> data){
        ArrayList<U> lines = new ArrayList<U>();
        for(T dto: data) {
            lines.add(ToData(dto));
        }
        return lines;
    }
}
