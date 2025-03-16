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
|Multiply
|Divide;;

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
let rec checkParaenthesis treeStr parenCount = match treeStr with 
|"" -> parenCount = 0;
|_ -> let firstChar = String.sub treeStr 0 1 in 
      if firstChar = "(" then checkParaenthesis (String.sub treeStr 1 ((String.length treeStr) - 1)) (parenCount + 1)
      else if firstChar = ")" then 
           if parenCount = 0 then false
           else checkParaenthesis (String.sub treeStr 1 ((String.length treeStr) - 1)) (parenCount - 1)
      else checkParaenthesis (String.sub treeStr 1 ((String.length treeStr) - 1)) parenCount;;

let test = "()()";;
checkParaenthesis test 0 ;;

(*Question 4*)
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

let rec evaluate tree = match tree with 
|Leaf(a) -> a
|Tree(node) -> let first = evaluate node.left in let second = evaluate node.right in match node.operator with
               |Add -> add first second
               |Sub -> sub first second
               |Mul -> mul first second
               |Div -> div first second;;

let example_tree = Tree{
  operator = Add;
  left=Tree{
    operator=Add;
    left = Leaf (Float(2.0));
    right = Leaf (Int(1))
  };
  right=Tree{
    operator=Sub;
    left=Leaf (Int(2));
    right=Leaf (Int(1))
  };
}

let x = evaluate example_tree;;

(*Work on question 5*)
type expr =
| Const of int
| Var of string
| Add of expr * expr
| Mul of expr * expr;;


let mulTest const var = if const = 1 then Var(var)
                        else if const = 0 then Const(const)
                        else Mul(Const(const), Var(var));;

let addTest const var = if const = 0 then Var(var)
                        else Add(Const(const), Var(var));;

let addThree const first second = match first,second with
|Const(a), _ -> Add(Const(const + a), second)
|_, Const(a) -> Add(Const(const + a), first)
|_,_ -> Add(Const(const), Add(first,second));;

let addFour first second third fourth = match first,second with
|Const(a), _ -> Add(Const(a), second)
|_, Const(a) -> Add(Const(a), first)
|_,_ -> Add(Const(0), Add(first,second));;

let addFinale result result2 = 
match result, result2 with 
|Const(a), Const(b) -> Const(a+b)
|Const(a), Var(b) -> addTest a b
|Const(a), Add(b,c) -> addThree a b c
|Const(a), Mul(b,c) -> Add(Const(a), Mul(b,c))
|Var(a), Const(b) -> addTest b a
|Var(a), Var(b) -> Add(Var(a), Var(b))
|Var(a), Add(b,c) -> Add(Var(a), Add(b,c))
|Var(a), Mul(b,c) -> Add(Var(a), Mul(b,c))
|Add(a,b), Const(c) -> addThree c a b
|Add(a,b), Var(c) -> Add(Add(a,b), Var(c))
|Add(a,b), Add(c,d) -> addFour a b c d
|Add(a,b), Mul(c,d) -> Add(Add(a,b), Mul(c,d))
|Mul(a,b), _ -> Add(Mul(a,b), result2);;
                                       
                                    

let rec mul first second = match first, second with 
|Const(a), Const(b) -> Const(a*b)
|Const(a), Var(b) -> mulTest a b
|Const(a), Add(b,c) -> b
|Const(a), Mul(b,c) -> b
|Var(a), Const(b) ->  mulTest b a 
|Var(a), Var(b) -> Mul(Var(a), Var(b))
|Var(a), Add(b,c) -> b
|Var(a), Mul(b,c) -> b
|Add(a,b), _ -> a
|Mul(a,b), _ -> a;;

let rec add first second = match first, second with 
|Const(a), Const(b) -> Const(a+b)
|Const(a), Var(b) -> addTest a b
|Const(a), Add(b,c) -> let result = add b c in 
                       (match result with 
                       |Const(d) -> Const(d+a)
                       |Var(d) -> addTest a d
                       |Add(d,e) -> addThree a d e
                       |Mul(d,e) -> Add(Const(a), Mul(d,e))
                       )
|Const(a), Mul(b,c) -> if a = 0 then Mul(b,c)
                       else Add(Const(a), Mul(b,c))
|Var(a), Const(b) -> addTest b a
|Var(a), Var(b) -> Add(Var(a), Var(b))
|Var(a), Add(b,c) -> let result = add b c in 
                     (match result with 
                     |Const(d) -> addTest d a
                     |Var(d) -> Add(Var(a), Var(d))
                     |Add(d,e) -> Add(Var(a), Add(d,e))
                     |Mul(d,e) -> Add(Var(a), Mul(d,e))
                     ) 
|Var(a), Mul(b,c) -> Add(Var(a), Mul(b,c))
|Add(a,b), _ -> let result = add a b in 
                       (match second with 
                        |Const(c) -> (match result with 
                                      |Const(d) -> Const(c+d)
                                      |Var(d) -> addTest c d
                                      |Add(d,e) -> addThree c d e
                                      |Mul(d,e) -> if c = 0 then Mul(d,e)
                                                   else Add(Mul(d,e), Const(c))
                                      )
                        |Var(c) -> (match result with 
                                    |Const(d) -> addTest d c
                                    |Var(d) -> Add(Var(d), Var(c))
                                    |Add(d,e) -> Add(Add(d,e), Var(c))
                                    |Mul(d,e) -> Add(Mul(d,e), Var(c))
                                   )
                        |Add(c,d) -> let result2 = add c d in addFinale result result2
                        |Mul(c,d) -> let result2 = mul c d in addFinale result result2
                      )
|Mul(a,b), _ -> a;;


let x = add (Add(Const(2), Const(3))) (Mul(Const(2),Var("b")));;