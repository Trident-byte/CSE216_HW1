(*Worked on part 1 of question 1*)
type 'a binarytree =
   |Leaf
   |Node of 'a * 'a binarytree * 'a binarytree;;


let rec walk_inorder tree = match tree with
    |Leaf -> []
    |Node(data, left, right) -> walk_inorder(left)@[data]@walk_inorder(right);;


let test_inorder () = walk_inorder (Node(1,Node(2,Node(4,Leaf,Leaf),Node(5,Leaf,Leaf)),Node(3,Node(9,Leaf,Leaf),Node(0,Leaf,Leaf))));;

(*Question 2*)
type ('a,'b) tree =
| Leaf of 'a
| Tree of ('a, 'b) node
and
('a,'b) node = {operator: 'b;
left: ('a,'b) tree;
right: ('a,'b) tree
};;

type operator = 
|Add
|Sub
|Mul
|Div;;

type operand =
|Int of int
|Float of float;;

type sign =
|Operand of operand
|Operator of operator;;

let example_tree = Tree{
  operator = Add;
  left=Tree{
    operator=Add;
    left = Leaf (Int(2));
    right = Leaf (Int(1))
  };
  right=Tree{
    operator=Sub;
    left=Leaf (Int(2));
    right=Leaf (Int(1))
  };
}

let items_of_parameter_type tree chosenType = match chosenType with
|Operator(_) -> let rec find_operators tree = match tree with
               |Leaf(a) -> []
               |Tree(node) -> find_operators(node.left)@[Operator(node.operator)]@find_operators(node.right) in find_operators tree
|Operand(_) -> let rec find_operand tree = match tree with
               |Leaf(a) -> [Operand(a)]
               |Tree(node) -> find_operand(node.left)@find_operand(node.right) in find_operand  tree;;

let test = Operand(Int(1));;
items_of_parameter_type example_tree  test;;

(*Question 3*)

let createLeaf numStr = try Leaf(Int(int_of_string numStr))
                        with Failure("int_of_string") -> Leaf(Float(float_of_string numStr));;

(*Checks to see if either the left or right tree are int leaves, if so convert them to float leaves*)
let checkForFloats left right = match left,right with 
|Leaf(Int(a)), Leaf(Int(b)) -> Leaf(Float(float_of_int a)), Leaf(Float(float_of_int b))
|Leaf(Int(a)), _ -> Leaf(Float(float_of_int a)), right
|_, Leaf(Int(b)) -> left, Leaf(Float(float_of_int b))
|_,_ -> (left,right);;

let findDelimiter str = if String.contains str ' ' then let first = String.index str ' ' in
     if String.contains str ')' then let second = String.index str ')' in 
        if first > second then second else first
     else first
  else if String.contains str ')' then String.index str ')'
  else -1;;

let rec infix_to_pre infix operands operators  = match infix with
|"" -> List.hd operands
|_->let remainder = (String.sub infix 1 ((String.length infix) - 1)) in  match (String.get infix 0) with
|'(' -> infix_to_pre remainder operands operators
|' ' -> infix_to_pre remainder operands operators
|')' -> (infix_to_pre remainder ([(List.hd operators) ^ " " ^ (List.hd (List.tl operands)) ^ " " ^(List.hd operands)]@(List.tl (List.tl operands))) (List.tl operators))
|_ -> let cutoff = findDelimiter infix in  
let subString = (String.sub infix 0 cutoff) in 
let rest = (String.sub infix (cutoff) ((String.length infix) - cutoff)) in
if Char.code (String.get infix 0) - 48 < 0 || Char.code (String.get infix 0) - 48 > 10 then infix_to_pre rest operands ([subString]@operators)
else infix_to_pre rest ([subString]@operands) operators;;

let rec build_tree_rec lst = match lst with
|[] -> failwith("Empty tree")
|[a] -> if Char.code (String.get a 0) - 48 < 0 || Char.code (String.get a 0) - 48 > 10 then failwith ("Operator missing operands")
else (createLeaf a, [])
|h::t -> if Char.code (String.get h 0) - 48 < 0 || Char.code (String.get h 0) - 48 > 10 then 
let (leftTree, rest) = build_tree_rec t in let (rightTree, leftover) = build_tree_rec rest in 
match h with 
|"+" -> (Tree{operator = Add; left = leftTree; right = rightTree}, leftover)
|"-" -> (Tree{operator = Sub; left = leftTree; right = rightTree}, leftover)
|"/" -> (Tree{operator = Div; left = leftTree; right = rightTree}, leftover)
|"*" -> (Tree{operator = Mul; left = leftTree; right = rightTree}, leftover)
|_ -> let (leftFloat, rightFloat) = checkForFloats leftTree rightTree in 
match h with
|"+." -> (Tree{operator = Add; left = leftFloat; right = rightFloat}, leftover)
|"-." -> (Tree{operator = Sub; left = leftFloat; right = rightFloat}, leftover)
|"/." -> (Tree{operator = Div; left = leftFloat; right = rightFloat}, leftover)
|"*." -> (Tree{operator = Mul; left = leftFloat; right = rightFloat}, leftover)
|_ -> failwith "Invalid character"

else (createLeaf h, t);;  

let build_tree str = let prefix = String.split_on_char ' ' (infix_to_pre str [] []) in let (tree, empty) = build_tree_rec prefix in tree;;

let t = build_tree "(1 - (2 * 4))";;

(*Question 4*)

(*The four functions add, subtract, multiply or divide and also convert ints to floats if needed 
  for the pattern matching*)
let add first second = match first, second with 
          |(Int(a), Int(b)) -> Int(a+b)
          |(Int(a), Float(b)) -> Float((float_of_int a ) +. b)
          |(Float(a), Int(b)) -> Float((float_of_int b ) +. a)
          |(Float(a), Float(b)) -> Float(a +. b)

let sub first second = match first, second with 
          |(Int(a), Int(b)) -> Int(a-b)
          |(Int(a), Float(b)) -> Float((float_of_int a ) -. b)
          |(Float(a), Int(b)) -> Float((float_of_int b ) -. a)
          |(Float(a), Float(b)) -> Float(a +. b)


let mul first second = match first, second with 
          |(Int(a), Int(b)) -> Int(a*b)
          |(Int(a), Float(b)) -> Float((float_of_int a ) *. b)
          |(Float(a), Int(b)) -> Float((float_of_int b ) *. a)
          |(Float(a), Float(b)) -> Float(a *. b)

let div first second = match first, second with 
          |(Int(a), Int(b)) -> Int(a/b)
          |(Int(a), Float(b)) -> Float((float_of_int a ) /. b)
          |(Float(a), Int(b)) -> Float((float_of_int b ) /. a)
          |(Float(a), Float(b)) -> Float(a /. b)

(*Pattern matches the types to know which function to call*)
let rec evaluate tree = match tree with 
|Leaf(a) -> a
|Tree(node) -> let first = evaluate node.left in let second = evaluate node.right in match node.operator with
               |Add -> add first second
               |Sub -> sub first second
               |Mul -> mul first second
               |Div -> div first second;;


let t = build_tree "(1 + (2 * 3))";;
let v = evaluate t;; (* v should be the int value 7 *)

(*Work on question 5*)
type expr =
| Const of int
| Var of string
| Add of expr * expr
| Mul of expr * expr;;

(*Checks edge cases for multiplication and addition*)
let mulTest const var = if const = 1 then Var(var)
                        else if const = 0 then Const(const)
                        else Mul(Const(const), Var(var));;

let addTest const var = if const = 0 then Var(var)
                        else Add(Const(const), Var(var));;

(*Preforms communative property with one constant and an irreducible addition*)
let addThree const first second = match first,second with
|Const(a), _ -> Add(Const(const + a), second)
|_, Const(a) -> Add(Const(const + a), first)
|_,_ -> Add(Const(const), Add(first,second));;

(*Checks all cases that are reducible and the wildcard pattern represents irreducible relationships*)
let rec mul first second = match first, second with 
|Const(a), Const(b) -> Const(a*b)
|Const(a), Var(b) -> mulTest a b
|Const(a), Add(b,c) -> Add(Mul(Const(a), b), Mul(Const(a), c))
|Const(a), Mul(Const(b),c) -> Mul(Const(a*b), c)
|Const(a), Mul(b, Const(c)) -> Mul(Const(a*c), b)
|Var(a), Const(b) ->  mulTest b a 
|Var(a), Add(b,c) -> Add(Mul(Var(a), b), Mul(Var(a), c))
|Add(a,b), Const(c) -> Add(Mul(Const(c), a), Mul(Const(c), b))
|Add(a,b), Add(c,d) -> Add(Mul(c, Add(a,b)), Mul(d, Add(a,b)))
|Add(a,b), Mul(c,d) -> Add(Mul(a, Mul(c,d)), Mul(b, Mul(c,d)))
|Mul(Const(a),b), Const(c) -> Mul(Const(a*c), b)
|Mul(a, Const(b)), Const(c) -> Mul(Const(b*c), a)
|Mul(a,b), Add(c,d) -> Add(Mul(c, Mul(a,b)), Mul(d, Mul(a,b))) 
|Mul(Const(a),b),Mul(Const(c),d) -> Mul(Const(a*c),Mul(b,d))
|Mul(Const(a),b),Mul(c,Const(d)) -> Mul(Const(a*d), Mul(b,c))
|Mul(a, Const(b)), Mul(Const(c), d) -> Mul(Const(b*c), Mul(a,d))
|Mul(a, Const(b)), Mul(c, Const(d)) -> Mul(Const(b*d), Mul(a,c))
|_,_ -> Mul(first, second);;

(*Checks all cases that are reducible and the wildcard pattern represents irreducible relationships*)
let rec add first second = match first, second with 
|Const(a), Const(b) -> Const(a+b)
|Const(a), Var(b) -> addTest a b
|Const(a), Add(b,c) -> addThree a b c
|Const(a), Mul(b,c) -> if a = 0 then Mul(b,c)
                       else Add(Const(a), Mul(b,c))
|Var(a), Const(b) -> addTest b a
|Add(a,b), Const(c) -> addThree c a b
|Add(Const(a),b), Add(c, Const(d)) -> Add(Const(a+d), Add(b,c))
|Add(Const(a),b), Add(Const(c), d) -> Add(Const(a+c), Add(b,d))
|Add(a,Const(b)), Add(c, Const(d)) -> Add(Const(b+d), Add(a,c))
|Add(a,Const(b)), Add(Const(c),d) -> Add(Const(b+c), Add(a,d))
|_,_ -> Add(first,second);;

(*Recursively reduces by first reducing the left and right followed by using add or mul based on the expression*)
let rec reduce expr = 
let newExpr = match expr with
|Const(a) -> Const(a)
|Var(a) -> Var(a)
|Add(a,b) -> let c = reduce a in let d = reduce b in
             add c d
|Mul(a,b) -> let c = reduce a in let d = reduce b in
             mul c d 
(*If there has been a reduction it will make sure the statement is irreducible by calling reduce again,*)
in if newExpr = expr then expr else reduce newExpr;;


let x = reduce (Mul(Add(Var "x", Const 2), Add(Var "x", Const 3)));;