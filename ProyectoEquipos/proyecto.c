#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <ctype.h>

#define TAM_MEMORIA 256
#define CLAVE_MEMORIA 1234

typedef struct {
    char mensaje[TAM_MEMORIA];
    int estado; // 0: disponible, 1: cliente a router, 2: router a servidor, 3: servidor a router, 4: router a cliente
} MemoriaCompartida;

void servidor(MemoriaCompartida *memoria) {
    while (1) {
        if (memoria->estado == 2) {
            printf("Servidor: Recibiendo mensaje de router.\n");
            for (int i = 0; memoria->mensaje[i]; i++) {
                memoria->mensaje[i] = toupper(memoria->mensaje[i]);
            }
            printf("Servidor: Modificando mensaje.\n");
            memoria->estado = 3;
            printf("Servidor: Enviando mensaje modificado al router.\n");
        }
        usleep(100000);
    }
}

void router(MemoriaCompartida *memoria) {
    while (1) {
        if (memoria->estado == 1) {
            printf("Router: Enviando mensaje al servidor.\n");
            memoria->estado = 2;
        }
        if (memoria->estado == 3) {
            printf("Router: Enviando mensaje modificado a cliente.\n");
            memoria->estado = 4;
        }
        usleep(100000);
    }
}

void cliente(MemoriaCompartida *memoria) {
    while (1) {
        printf("Cliente: Por favor, ingrese su mensaje (escriba 'salir' para terminar):\n");
        fgets(memoria->mensaje, TAM_MEMORIA, stdin);

        if (strncmp(memoria->mensaje, "salir", 5) == 0) {
            break;
        }

        memoria->estado = 1;
        printf("Cliente: Enviando mensaje al router.\n");

        while (memoria->estado != 4) {
            usleep(100000);
        }

        printf("Cliente: Mensaje modificado recibido: %s\n", memoria->mensaje);
        memoria->estado = 0;
    }
}

int main() {
    int id_memoria;
    MemoriaCompartida *memoria;

    id_memoria = shmget(CLAVE_MEMORIA, sizeof(MemoriaCompartida), 0666 | IPC_CREAT);
    if (id_memoria == -1) {
        perror("Error en shmget");
        exit(1);
    }

    memoria = (MemoriaCompartida *)shmat(id_memoria, NULL, 0);
    if (memoria == (void *)-1) {
        perror("Error en shmat");
        exit(1);
    }

    pid_t pid1, pid2;

    pid1 = fork();
    if (pid1 == 0) {

        servidor(memoria);
        exit(0);
    } else {
        pid2 = fork();
        if (pid2 == 0) {
            router(memoria);
            exit(0);
        } else {
            cliente(memoria);
        }
    }

    shmdt(memoria);
    return 0;
}