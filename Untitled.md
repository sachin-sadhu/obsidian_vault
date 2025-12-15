
language ESSENCE' 1.0

$$ Parameters $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

given stack_height : int(1..)
given max_moves : int(1..)
given num_routes : int(1..)
given num_stackers : int(1..)
given initial_stacks : matrix indexed by [int(1..num_routes+1), int(1..stack_height)] of int(0..num_routes)

$ all arrays are 0 indexed
$ start moves from 1

$$ Decision Variables $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

find state : matrix indexed by [int(0..max_moves), int(0..num_routes), int(0..stack_height-1)] of int(0..num_routes)
find move_from : matrix indexed by [int(0..max_moves), int(0..num_stackers-1)] of int(-1..num_routes)
find move_to : matrix indexed by [int(0..max_moves), int(0..num_stackers-1)] of int(-1..num_routes)
find moving_value : matrix indexed by[int(0..max_moves), int(0..num_stackers-1)] of int(-1, 1..num_routes)
find moves_required : int(0..max_moves)

$branching on [move_from, move_to, moves_required]
branching on [moves_required, move_from, move_to]

minimising moves_required

$$ Constraints $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

such that

$$ Set Initial State $

forall stacker : int(0..num_stackers-1) .
    (move_from[0, stacker] = -1) /\ (move_to[0, stacker] = -1) /\ (moving_value[0, stacker] = -1),

forall array : int(0..num_routes) .
    forall index : int(0..stack_height-1) .
        state[0, array, index] = initial_stacks[array+1, index+1],

forall moveNumber : int(1..max_moves) .
    (moveNumber <= moves_required) <-> (exists stacker : int(0..num_stackers-1) . move_from[moveNumber, stacker] != -1),

$ Do not allow moves to the same array.
forall moveNumber : int(1..max_moves) .
    forall stacker : int(0..num_stackers-1) .
        (move_from[moveNumber, stacker] >= 0 /\ move_to[moveNumber, stacker] >= 0) -> (move_from[moveNumber, stacker] != move_to[moveNumber, stacker]),

$ If a stack is not involved in the move, just copy over elements over.
forall moveNumber : int(1..max_moves) . 
    forall array : int(0..num_routes) . 
        (forall stacker : int(0..num_stackers-1) .
            (array != move_from[moveNumber, stacker]) /\ (array != move_to[moveNumber, stacker])
        ) -> 
        (forall index : int(0..stack_height-1) .
            state[moveNumber, array, index] = state[moveNumber-1, array, index]
        ),

$ Stack to move an element FROM must contain a non-zero element.
forall moveNumber : int(1..max_moves) . 
    forall stacker : int(0..num_stackers-1) .
        (move_from[moveNumber, stacker] >= 0) -> (exists index : int(0..stack_height-1) . 
            state[moveNumber-1, move_from[moveNumber, stacker], index] != 0
        ),

$ Stack to move an element TO must contain a 0 element.
forall moveNumber : int(1..max_moves) .
    forall stacker : int(0..num_stackers-1) .
    (move_to[moveNumber, stacker] >= 0) -> (exists index : int(0..stack_height-1) .
        state[moveNumber-1, move_to[moveNumber, stacker], index] = 0
    ),

$ Stack which has an element removed from should have all elements the same except for top most non-zero index, which becomes 0.
$ move_value[moveNumber] = top most non-zero index
forall moveNumber : int(1..max_moves) .
    forall stacker : int(0..num_stackers-1) .
        (move_from[moveNumber, stacker] >= 0) -> (
            $ Find top most non-zero index
            forall index : int(0..stack_height-1) . 
                (((state[moveNumber-1, move_from[moveNumber, stacker], index] != 0) /\
                $ Elements below all != 0
                (forall belowIndex : int(0..stack_height-1) .
                    (belowIndex < index) -> (state[moveNumber-1, move_from[moveNumber, stacker], belowIndex] != 0)) /\
                $ Elements above all = 0
                (forall aboveIndex : int(0..stack_height-1) .
                    (aboveIndex > index) -> (state[moveNumber-1, move_from[moveNumber, stacker], aboveIndex] = 0))) 
                -> 
                $ Set index value to 0 and set moving_value
                ((state[moveNumber, move_from[moveNumber, stacker], index] = 0) /\ 
                (moving_value[moveNumber, stacker] = state[moveNumber-1, move_from[moveNumber, stacker], index])))
                /\
                $ If not top most non-zero index, just copy element over
                (!(((state[moveNumber-1, move_from[moveNumber, stacker], index] != 0) /\
                (forall belowIndex : int(0..stack_height-1) .
                    (belowIndex < index) -> (state[moveNumber-1, move_from[moveNumber, stacker], belowIndex] != 0)) /\
                (forall aboveIndex : int(0..stack_height-1) .
                    (aboveIndex > index) -> (state[moveNumber-1, move_from[moveNumber, stacker], aboveIndex] = 0))))
                -> 
                (state[moveNumber, move_from[moveNumber, stacker], index] = state[moveNumber-1, move_from[moveNumber, stacker], index]))
        ),

$ Stack which has an element place to should have all elements the same except for bottom most zero index, which becomes move_value[moveNumber].
forall moveNumber : int(1..max_moves) .
    forall stacker : int(0..num_stackers-1) .
        (move_to[moveNumber, stacker] >= 0) -> (
            forall index : int(0..stack_height-1) .
                (((state[moveNumber-1, move_to[moveNumber, stacker], index] = 0) /\
                $ All elements below should != 0
                (forall belowIndex : int(0..stack_height-1) .
                    (belowIndex < index) -> (state[moveNumber-1, move_to[moveNumber, stacker], belowIndex] != 0)))
                ->
                (state[moveNumber, move_to[moveNumber, stacker], index] = moving_value[moveNumber, stacker]))
                /\
                $ Other elements are just copied over
                (!(((state[moveNumber-1, move_to[moveNumber, stacker], index] = 0) /\
                (forall belowIndex : int(0..stack_height-1) .
                    (belowIndex < index) -> (state[moveNumber-1, move_to[moveNumber, stacker], belowIndex] != 0))))
                ->
                (state[moveNumber, move_to[moveNumber, stacker], index] = state[moveNumber-1, move_to[moveNumber, stacker], index]))
        ),


$ Once goal state is reached, copy all stack values over. Also move_from, move_to, move_value should have values set to -1 (no move).
forall moveNumber : int(1..max_moves) .
    (forall array : int(0..num_routes) . 
        forall index : int(0..stack_height-1) . 
            (state[moveNumber-1, array, index] in int(0,array))
            /\
            ((index < stack_height-1 /\ state[moveNumber-1, array, index] = 0) -> state[moveNumber-1, array, index+1] = 0)
    ) -> 
    (
        $ Copy values over
        (forall array : int(0..num_routes) .
            forall index : int(0..stack_height-1) .
                state[moveNumber, array, index] = state[moveNumber-1, array, index]
        ) /\
        (
            forall stacker : int(0..num_stackers-1) .
                move_from[moveNumber, stacker] = -1 /\ move_to[moveNumber, stacker] = -1 /\ moving_value[moveNumber, stacker] = -1
        )
    ),

$ If goal state has not yet been reached, there should be at least 1 stacker making a valid move.
forall moveNumber : int(1..max_moves) .
    (exists array : int(0..num_routes) .
        exists index : int(0..stack_height-1) .
            state[moveNumber-1, array, index] != array /\ state[moveNumber-1, array, index] != 0
    ) -> 
    (exists stacker : int(0..num_stackers-1) . move_from[moveNumber, stacker] != -1),

$ Goal state MUST be reached.
exists moveNumber : int(0..max_moves) .
    forall array : int(0..num_routes) .
        forall index : int(0..stack_height-1) .
            ((state[moveNumber, array, index] in int(0,array))
            /\
            ((index < stack_height-1) /\ (state[moveNumber, array, index] = 0) -> (state[moveNumber, array, index+1] = 0))
            ),

$ Heuristic Constraints $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$ Don't move from arrays fully sorted (can still place elements). 
forall moveNumber : int(1..max_moves) .
    forall stacker : int(0..num_stackers-1) .
        (move_from[moveNumber, stacker] >= 0) -> (exists index : int(0..stack_height-1) .
            (state[moveNumber-1, move_from[moveNumber, stacker], index] != move_from[moveNumber, stacker]) /\ (state[moveNumber-1, move_from[moveNumber, stacker], index] != 0)
        ),

$ Do not allow transitive moves. If a stacker performs 1 -> 2, then on next move NO stacker should perform 2 -> x.
forall moveNumber : int(1..max_moves-1) .
    forall stacker1, stacker2 : int(0..num_stackers-1) .
        (move_from[moveNumber+1, stacker1] >= 0 /\ move_to[moveNumber, stacker2] >= 0)
        -> 
        (move_from[moveNumber+1, stacker1] != move_to[moveNumber, stacker2]),

$ Implied Constraints $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$ Ensure the number of occurences of each variable stays constant
forall moveNumber : int(0..max_moves) .
    forall element : int(0..num_routes) .
        (sum array : int(0..num_routes) .
            sum index : int(0..stack_height-1) .
                (state[moveNumber, array, index] = element)
        ) = 
        (sum array : int(0..num_routes) .
            sum index : int(0..stack_height-1) .
                (state[0, array, index] = element)
        ),

$ Ensure if move_from is -1, the move_to, and move_value is also, vice versa
forall moveNumber : int(1..max_moves) .
    forall stackers : int(0..num_stackers-1) .
        ((move_from[moveNumber, stackers] = -1) <-> (move_to[moveNumber, stackers] = -1)) /\
        ((move_from[moveNumber, stackers] = -1) <-> (moving_value[moveNumber, stackers] = -1)),


$ Symmetrey Breaking Constraints $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$ For every move, force lower index stackers to be used first.
forall moveNumber : int(1..max_moves-1) .
    forall stacker1, stacker2 : int(0..num_stackers-1) .
        (stacker1 < stacker2 /\ move_from[moveNumber, stacker1] = -1) -> (move_from[moveNumber,stacker2] = -1),

$ For consecutive moves that DO NOT interfere with each other, order them lexigraphically.
forall moveNumber : int(1..max_moves-1) .
    forall stacker1, stacker2 : int(0..num_stackers-1) .
        ((move_from[moveNumber, stacker1] != -1) /\ (move_from[moveNumber+1, stacker2] != -1) /\
        (move_from[moveNumber, stacker1] != move_to[moveNumber+1, stacker2]) /\
        (move_to[moveNumber, stacker1] != move_from[moveNumber+1, stacker2]) /\
        (move_from[moveNumber, stacker1] != move_from[moveNumber+1, stacker2]) /\
        (move_to[moveNumber, stacker1] != move_to[moveNumber+1, stacker2])
        ) -> ([move_from[moveNumber, stacker1], move_to[moveNumber, stacker1]] <=lex [move_from[moveNumber+1, stacker2], move_to[moveNumber+1, stacker2]]),

$ Within a move, order stackers lexigrahpically.
forall moveNumber : int(1..max_moves) .
    forall stacker1, stacker2 : int(0..num_stackers-1) . 
        (stacker1 < stacker2 /\ move_from[moveNumber,stacker1] != -1 /\ move_from[moveNumber,stacker2] != -1) -> 
        ([move_from[moveNumber, stacker1], move_to[moveNumber, stacker1]]) <=lex ([move_from[moveNumber, stacker2], move_to[moveNumber, stacker2]]),

$ Parallel Constraints $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

$ 2 stackers should not interact with the same stacks in the same move.
forall moveNumber : int(1..max_moves) .
    forall stacker1, stacker2 : int(0..num_stackers-1) .
        (stacker1 != stacker2 /\ move_from[moveNumber, stacker1] != -1 /\ move_from[moveNumber,stacker2] != -1) ->
        (
            (move_from[moveNumber, stacker1] != move_from[moveNumber, stacker2]) $ Don't pick from same stack
            /\
            (move_to[moveNumber, stacker1] != move_to[moveNumber, stacker2]) $ Don't place to same stack
            /\
            (move_from[moveNumber, stacker1] != move_to[moveNumber, stacker2]) $ Don't take from a stack being placed to
            /\
            (move_to[moveNumber, stacker1] != move_from[moveNumber, stacker2]) $ Don't place to a stack being removed
        )