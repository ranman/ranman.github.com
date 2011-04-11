/* Object and Item Definitions */
:- dynamic item_at/2.
    item_at(broom, 'Dorm Room').
    item_at(flowers, 'The Quad').
    item_at(smartness, 'Capstone').

:- dynamic object_at/2.
    object_at(desk, 'Dorm Room').
    object_at(shower, 'Dorm Room').

:- dynamic use/1.
    use(broom) :-
        location(you, Place),
        character_holding(broom),
        assert(clean(Place)),
        writeln('You tidy up the place').
    use(broom) :-
        writeln('You need to take(broom), before using it').

    use(friend) :-
        location(you, 'Dorm Room'),
        character_holding(friend),
        retract(character_holding(friend)),
        assert(item_at(friend, 'Dorm Room')),
        writeln('You leave your friend in your room.').
    use(friend) :-
        location(you, _),
        writeln('There is no use for friends here').

    use(shower) :-
        writeln('Now you''re fresh and ready to be a social butterfly.'),
        assert(clean(you)),
        assert(access('Social Life')).

    use(flowers) :-
        location(you, 'Social Life'),
        character_holding(flowers),
        location(girlfriend, 'Social Life'),
        assert(access('Girl Friend''s Room')),
        retract(character_holding(flowers)),
        writeln('Gave the girl the flowers').
    
    use(small_key) :-
        location(you, 'Social Life'),
        character_holding(small_key),
        object_at(locked_door, 'Social Life'),
        assert(access('Girlfriend''s Room')),
        retract(object_at(locked_door, 'Social Life')).
    use(small_key) :-
        writeln('You can''t find any use for that.').
    use(_) :-
        writeln('You can''t use that.').
