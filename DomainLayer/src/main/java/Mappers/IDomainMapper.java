package Mappers;

import java.util.ArrayList;

public abstract class IDomainMapper<T,U> {
    public abstract T ToDomain(U data);
    public abstract U ToDTO(T data);

    public ArrayList<T> ToDomainList(Iterable<U> data){
        ArrayList<T> dtos = new ArrayList<T>();
        for(U line: data) {
            dtos.add(ToDomain(line));
        }
        return dtos;
    }

    public ArrayList<U> ToDTOList(Iterable<T> data){
        ArrayList<U> lines = new ArrayList<U>();
        for(T dto: data) {
            lines.add(ToDTO(dto));
        }
        return lines;
    }
}

