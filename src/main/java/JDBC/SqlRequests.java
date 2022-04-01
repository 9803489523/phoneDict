package JDBC;

public class SqlRequests {

    public static final String findByOwner=
            """
                    select d.d_id as id,
                           o.o_name as owner,
                           ad.a_name as address,
                           pn.p_number as phone_number
                      from dict d
                        inner join addresses ad
                        on d.a_id=ad.a_id
                          inner join owners o
                          on o.o_id=d.o_id
                            inner join phone_numbers pn
                            on pn.p_id=d.p_id
                      where o.o_name=?
                    ;
                    """;

    public static final String findByAddress=
            """
                    select d.d_id as id,
                           o.o_name as owner,
                           ad.a_name as address,
                           pn.p_number as phone_number
                      from dict d
                        inner join addresses ad
                        on d.a_id=ad.a_id
                          inner join owners o
                          on o.o_id=d.o_id
                            inner join phone_numbers pn
                            on pn.p_id=d.p_id
                      where ad.a_name=?
                    ;
                    """;

    public static final String findByPhoneNumber=
            """
                    select d.d_id as id,
                           o.o_name as owner,
                           ad.a_name as address,
                           pn.p_number as phone_number
                      from dict d
                        inner join addresses ad
                        on d.a_id=ad.a_id
                          inner join owners o
                          on o.o_id=d.o_id
                            inner join phone_numbers pn
                            on pn.p_id=d.p_id
                      where pn.p_number=?
                    ;
                    """;

    public static final String findAll=
            """
                    select d.d_id as id,
                           o.o_name as owner,
                           ad.a_name as address,
                           pn.p_number as phone_number
                      from dict d
                        inner join addresses ad
                        on d.a_id=ad.a_id
                          inner join owners o
                          on o.o_id=d.o_id
                            inner join phone_numbers pn
                            on pn.p_id=d.p_id
                    ;""";

    public static final String searchAddressId=
            """
                    select a.a_id as id
                        from addresses a
                        where a.a_name=?;
                    """;

    public static final String searchOwnerId=
            """
                    select o.o_id as id
                      from owners o
                      where o.o_name=?;
                    """;
    public static final String searchPhoneNumberId=
            """
                    select p.p_id as id
                        from phone_numbers p
                        where p.p_number=?;
                    """;
    public static final String insertIntoOthers=
            """
                    insert into addresses(a_name) values
                      (?);
                                        
                    insert into owners(o_name) values
                      (?);
                                        
                    insert into phone_numbers(p_number) values
                      (?);
                    
                    """;
    public static final String insertAddress=
            """
                    insert into addresses(a_name) values
                      (?);
                    """;
    public static final String insertOwner=
            """
                    insert into owners(o_name) values
                      (?);
                    """;
    public static final String insertPhone=
            """
                    insert into phone_numbers(p_number) values
                      (?);
                    """;
    public static final String insertIntoMain=
            """
                    insert into dict(o_id,a_id,p_id) values
                      (?,?,?);
                    """;
    public static final String getDictById=
            """
                    select d.d_id as id,
                           o.o_name as owner,
                           ad.a_name as address,
                           pn.p_number as phone_number
                      from dict d
                        inner join addresses ad
                        on d.a_id=ad.a_id
                          inner join owners o
                          on o.o_id=d.o_id
                            inner join phone_numbers pn
                            on pn.p_id=d.p_id
                      where d.d_id =?
                    ;
                    """;
    public static final String updateDict=
            """
                    update dict d
                      set a_id=?,
                          o_id=?,
                          p_id=?
                      where d.d_id=?
                     ;
                    """;
}
