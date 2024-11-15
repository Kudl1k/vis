module DomainLayer {
    exports DomainModels;
    exports Mappers;
    exports Services;

    requires java.sql;
    requires DataLibrary;
    requires static lombok;
}