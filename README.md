# Laberinto Backtracking ⚐
> ### Proyecto EDA: equipo #1
>> - Evelyn Gómez Aristizabal
>> - Ana Sofia Henao Bedoya
>> - Isabella Ramírez Tobón
>> - Juan Pablo Alzate Atehortua
>> - Julián Marín Ramírez

## Descripción
El objetivo es encontrar rutas válidas que conecten el punto de inicio con la salida dentro de un laberinto representado por una matriz de celdas (en este caso tomamos inicio como la esquina superior izquierda y meta como la esquina inferior derecha del laberinto)
> Las casillas pueden ser muros (`#`), espacios libres (` `), rutas activas (`.`) o caminos sin salida (`I`)

El algoritmo de Backtracking explora **recursivamente** las cuatro direcciones posibles (derecha, abajo, izquierda, arriba) y **retrocede** (limpiando el rastro) si encuentra un muro o un camino sin salida

En el proyecto abordamos dos métodos:
- Mostrar la primera solución encontrada
- Encontrar y almacenar todas las soluciones posibles

Además de la **interfaz gráfica** para visualizar el laberinto, renderizar el camino tomado y explorar el catálogo de soluciones de forma interactiva.

## Pseudocódigo
Planteamiento general para problemas de backtracking:
```java
Función backtracking(...): {
	si condiciónSalida entonces: {
		guardarSolución()
		volver
	}
	recorrer opción en conjuntoOpciones: {
		si opciónValida(opción) entonces: {
			elegir(opción)
			backtracking(...)
			deshacer(opción)
		}
	}
	volver
}
