CC = gcc
CCFLAGS = -std=c99  -Wall -Wextra -Werror -g

.PHONY: clean

snakes_and_ladders: snakes_and_ladders.o markov_chain.o linked_list.o
	$(CC) $(CCFLAGS) snakes_and_ladders.o markov_chain.o linked_list.o -o snakes_and_ladders

snakes_and_ladders.o: snakes_and_ladders.c markov_chain.h
	$(CC) $(CCFLAGS) -c snakes_and_ladders.c

markov_chain.o: markov_chain.c markov_chain.h linked_list.h
	$(CC) $(CCFLAGS) -c markov_chain.c

linked_list.o: linked_list.h linked_list.c
	$(CC) $(CCFLAGS) -c linked_list.c

tweets_generator: tweets_generator.o markov_chain.o linked_list.o
	$(CC) $(CFLAGS) tweets_generator.o markov_chain.o linked_list.o -o tweets_generator

tweets_generator.o: tweets_generator.c markov_chain.h
	$(CC) $(CFLAGS) -c tweets_generator.c

clean:
	rm snakes_and_ladders *.o
