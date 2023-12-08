




to add this project to another project:

`make install`


in your other project: (assuming Android & gradle)


in module/app build.gradle:

add `implementation "com.persistentsystems.wrwebsocketclient:wrwebsocketclient:2.1.14"` to dependencies

in project build.gradle:

add `mavenLocal()` to allProjects { repositories {


