
#ifndef EMPTY_INT_BST_H
#define EMPTY_INT_BST_H

/*----------STRUCTS------------*/
struct Bst;
struct Node;



/*----------Functions------------*/
// creates new Bst and returns a pointer to it
struct Bst * new_bst();
// inserts new data to Bst tree (ordered)
int bst_insert(struct Bst *tree, int data);
// frees all the nodes and copied in the bst
void bst_free(struct Bst **tree);
// prints the bst that starts at root
void print_tree(struct Node *root);
#endif //EMPTY_INT_BST_H
