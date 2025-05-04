#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "empty_int_BST.h"

// 1a. Implement the struct “Node”
typedef struct Node
{
    int data;
    struct Node *left;
    struct Node *right;
} Node;

//2a. Implement the struct “Bst”
typedef struct Bst
{
    Node *root;
} Bst;

// 1b. Try to implement the function “Node* create_new_node(int data)”
Node* create_new_node(int data)
{
    Node * new_node = malloc(sizeof(Node));
    if (new_node == NULL) return NULL;
    new_node->data = data;  // (*new_node).data = data;
    new_node->left = NULL;
    new_node->right = NULL;
    return new_node;
}

// 2b. Implement the function “Bst* new_bst()” that creates an empty Bst
Bst* new_bst()
{
    Bst *tree = malloc(sizeof(Bst));
    if (tree == NULL) return NULL;
    tree->root = NULL;
    return tree;
}

//3. implement “int bst_insert(Bst *tree, int data)”
//  return 0 in case of failure and 1 in case of success
int bst_insert(Bst *tree, int data)
{
    Node *root = tree->root;
    Node *new_node = create_new_node(data);
    if (new_node == NULL) return 0;
    // if tree is empty:
    if (root == NULL)
    {
        tree->root = new_node;
        return 1;
    }
    while (root != NULL) {
        if (root->data < new_node->data) {
            // if no node on the right: add the new node
            if (root->right == NULL) {
                root->right = new_node;
                return 1;
            }
            root = root->right; // continue right

        } else {
            // if no node on the left: add the new node
            if (root->left == NULL) {
                root->left = new_node;
                return 1;
            }
            root = root->left; // continue left

        }
    }
    return 1;
}

// Hint: implement a recursive free_subtree for easier iteration over all
// nodes. what is the order of the frees?
void free_subtree(Node* root)
{
    if (root == NULL) return;
    free_subtree(root->left);
    free_subtree(root->right);
    free(root);
}

// 4. implement “void bst_free(Bst *tree)”
// Hint: implement a recursive free_subtree for easier iteration over all
// nodes.
void bst_free(Bst **tree) // why is a pointer to a pointer sent?
{
    free_subtree((*tree)->root);
    free(*tree);
    (*tree)->root = NULL;
}


// prints tree in ascending order
void print_tree(Node *root)
{
    if (root == NULL)
        return;
    print_tree(root->left);
    int p_val = root->data;
    printf("%d, ", p_val);
    print_tree(root->right);
}


//5. Run main()
// is the output in ascending order?
int main()
{
  // Bst * tree = new_bst();
  // bst_insert(tree, 5);
  // bst_insert(tree, 1);
  // bst_insert(tree, 3);
  // print_tree(tree->root);
  // bst_free(&tree);

}