package com.example.dbmgr.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DBDTO {

    private String baseHostName;
    private String migrationServerHostName;

    private String baseUserName;
    private String migrationServerUserName;

    private String basePassword;
    private String migrationServerPassword;

    private Integer basePort;
    private Integer migrationPort;
}
