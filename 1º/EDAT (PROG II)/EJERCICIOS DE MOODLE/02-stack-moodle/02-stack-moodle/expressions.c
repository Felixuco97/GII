
#include "expressions.h"
#include "stack.h"
#include "types.h"
#include <ctype.h> // for isdigit()
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define OPERATORS "+-*/%"

// is char c an operator?
Bool isOperator(char c) {
  char *oper = OPERATORS;
  char *pc;

  // exclude the char '\0' as operator
  if (c == '\0')
    return FALSE;

  // search for c in operators
  pc = strchr(oper, c);

  if (!pc)
    return FALSE;
  return TRUE;
}

// is char c an operand?
// accepts anything that is not an operator
Bool isOperand(char c) {
  Bool b;
  b = (isOperator(c) == TRUE) ? FALSE : TRUE;
  return b;
}

// is char c a digit
Bool isIntOperand(char c) { return isdigit(c) ? TRUE : FALSE; }

// evaluate expresion with operator oper
int evaluate(int arg1, int arg2, char oper) {
  int p = 0;
  
  switch (oper) {
  case '+':
    p = arg1 + arg2;
    break;
  case '-':
    p = arg1 - arg2;
    break;
  case '*':
    p = arg1 * arg2;
    break;
  case '/':
    p = arg1 / arg2;
    break;
  default:
    printf("Invalid operator");
  }
  return p;
}
/******************************************/

// START YOUR CODE

// return TRUE if string str has well-balanced parenthesis

Bool balancedParens(char *str) { 

  Status st = OK;
  Stack *s = NULL;
  int i = 0;
  char *c = NULL;

  s = stack_init();
  if(s == NULL)
    st = ERROR;

  while((str[i]!='\0') && (st == OK)){
    if(str[i] == '('){
      if(stack_push(s,&str[i]) == ERROR){
        st = ERROR;
        break;
      }
    }
    else if(str[i] == ')'){
      if(stack_isEmpty(s) == FALSE){
        c = (char*)stack_pop(s);
        if(c == NULL){
          st = ERROR;
        }
      }
      else{
        st = ERROR;
      }
    }
    i++;
  }

  if(stack_isEmpty(s) == FALSE){
    st = ERROR;
    while(stack_isEmpty(s) == FALSE){
    c = (char*)stack_pop(s);
    }
  }

  stack_free(s);

  if(st == OK){
    return TRUE;
  }else{
    return FALSE;
  }
}

// evaluate the postfix expression in expr
// return OK or ERROR
// if no error, *result contains the result of evaluating the expression

Status evalPostfix(char *expr, int *result) { 
  
  Status st = OK;
  Stack *s = NULL;
  int i = 0;
  int *arg1 = NULL;
  int *arg2 = NULL;
  int *res = NULL;
  int eval = 0;
  int *ele = NULL;

  s = stack_init();

  if(s == NULL)
    st = ERROR;;

  while((expr[i]!='\0') && (st == OK)){
    if(isIntOperand(expr[i]) == TRUE){
        ele = int_init(expr[i]-'0');
        if(stack_push(s,ele) == ERROR){
          st = ERROR;
          free(ele);
          *result = 0;
          break;
        }
    }
    else if(isOperator(expr[i]) == TRUE){
        if(stack_isEmpty(s) == FALSE){
          arg2 = (int*)stack_pop(s);
          if(arg2 == NULL){
            st = ERROR;
            *result = 0;
            break;
          }
        }
        else{
          st = ERROR;
        }
        if(stack_isEmpty(s) == FALSE){
          arg1 = (int*)stack_pop(s);
          if(arg1 == NULL){
            free(arg2);
            st = ERROR;
            break;
          }
          eval = evaluate(*arg1,*arg2,expr[i]);              
          free(arg1);
          free(arg2);
          if(stack_push(s,int_init(eval)) == ERROR){
            st = ERROR;
          }
        }
        else{
          free(arg2);
          st = ERROR;
        }
    }
    i++;
  }

  if(st == OK){
    res = stack_pop(s);
    if(res == NULL && stack_isEmpty(s) == FALSE){
      st = ERROR;
    }
    *result = *res;
    free(res);
  }
  if(stack_isEmpty(s) == FALSE){
    st = ERROR;
    while(stack_isEmpty(s) == FALSE){
    ele = (int*)stack_pop(s);
    free(ele);
    }
  }

  stack_free(s);
  return st; 
}
// END CODE