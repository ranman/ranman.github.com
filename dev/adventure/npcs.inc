/* * * * * * * * * * * * * * *
 * Adventure Game NPCs       *
 * * * * * * * * * * * * * * */
:- dynamic npc_holding/2.
    npc_holding(provost, diploma).

:- dynamic describe_npc/1.
    describe_npc(you) :-
        writeln('You are here').

    describe_npc(provost) :-
        writeln('The provost is standing at a podium, waiting for you.').

:- dynamic talk/1.
    talk(provost) :-
        writeln('Here take a diploma'),
        retract(npc_holding(provost,diploma)),
        assert(character_holding(diploma)).