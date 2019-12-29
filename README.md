# PhineLoops Projet
Projet M1 MIAGE parcours Classique 2019-2020
Contributors:  Taoufiq KOUNAIDI, Léa ONG , Duc-Chinh PHAM

## Compilation

Compilation with Maven :

```bash
mvn compile
```

Creation package .jar :

```bash
mvn package
```
## Execution program

Execution with the .jar file
```bash
java -jar phineloops-1.0-jar-with-dependencies.jar OPTIONS
```
|        OPTIONS                         | 			             |              Replace                                                                |
|----------------------------------------|--------------------------|-------------------------------------------------------------------------------------|
|Generate a grid [w/interface]           |`-g hxw -o fileOut [-G]`  |**h**: height  **w**:width  **fileOut**: fileName of generate grid  			    |
|Check if a grid is solved [w/interface] |`-c fileIn [-G]`          |**fileInput**: fileName to check      					       			    |
|Solve a grid [w/interface]              |`-s fileIn-o fileOut [-G]`|**fileIn**: fileName of input grid **fileOut**: fileName of generate solution        |

