Proyecto 03- Palabras contrarrloj

Integrantes:
1.- Cantero Zavaleta Héctor
2.- Sánchez Girón Yael Adrian

Este proyecto es un pequeño juego de formar palabras a través de una serie de letras que bien, pueden ser proporcionadas
por el usuario o generada aleatoriamente por la maquina.
El objetivo del juego es ver que persona tiene el mayor vocabulario, esto se puede checar a través de un archivo .txt 
que se crea al momento de inciar el programa 
Para la parte del comtador, que es una de las características principales de este proyecto, decidimos hacer uso de
hilos, esto ya que solamente se requería utilizar un contador simple, por lo que utilizar librerias de Java como Java Timer
hubiera sido un poco incesesario ya que no sacaríamos provecho a todas las funciones que esta librería tiene.

También se tuvo que hacer un clase para que el programa guardara todas las palabras y las guardara en algún lado, por lo que
decidimos hacer una implementación de un mapa hash que guardara árboles AVL, además de clases como Palbras, las cuales nos ayudan 
a poder validar las palabras que ingrese el usuario, con esta parte del proyecto tuvimos un dilema ya que, se hizo una delimitación
de que solamnte se aceptaran palabras de tamañao menor o igual a 9, sin embargo decidimos dejar esta reestricción ya que las letras
generadas solamente son 9 y de acuerdo con las reglas establecidas del proyecto, no deben de ser más que nueve, por lo que no habría
algún problema dejar dicha restricción.

Instrucciones de Ejecución:
1. Descargar el archivo del diccionario (ej. '0_palabras_todas.txt') y colocarlo dentro de una carpeta dedicada 
   (por ejemplo, una carpeta llamada 'diccionario' en la raíz del proyecto).
2. Abrir la terminal o línea de comandos en la carpeta donde están los archivos del proyecto.
3. Compilar todos los archivos fuente con el comando:
   javac *.java
4. Ejecutar la clase principal con el comando:
   java Main
5. El programa iniciará y solicitará ingresar la ruta absoluta o relativa de la carpeta del diccionario para cargar las palabras.

Entorno de Desarrollo:
- Versión de Java: Java Development Kit (JDK) 25.
- Utilizamos una combinación entre Emacs, VsCode y Nvim :D 

