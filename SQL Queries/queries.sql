--1--

select player_name from player where batting_hand = 'Left-hand bat' and country_name = 'England' order by player_name ;

--2--

SELECT player_name, DATE_PART('year',AGE(dob)) AS player_age FROM player  WHERE bowling_skill='Legbreak googly' AND DATE_PART('year',AGE(dob))>=28 ORDER BY player_age DESC, player_name;

--3--

select match_id, toss_winner from match where toss_decision = 'bat' order by match_id ;

--4--

SELECT over_id, runs_scored FROM (SELECT batsman_scored.match_id,batsman_scored.over_id, batsman_scored.innings_no, SUM(batsman_scored.runs_scored)+COALESCE(SUM(extra_runs.extra_runs),0) AS runs_scored FROM batsman_scored LEFT OUTER JOIN extra_runs ON batsman_scored.match_id=extra_runs.match_id AND batsman_scored.ball_id=extra_runs.ball_id AND batsman_scored.innings_no=extra_runs.innings_no AND batsman_scored.over_id=extra_runs.over_id WHERE batsman_scored.match_id=335987 GROUP BY batsman_scored.match_id,batsman_scored.over_id, batsman_scored.innings_no ORDER BY runs_scored DESC, batsman_scored.over_id) AS temp WHERE runs_scored>=7;

--5--

select player_name from player left join wicket_taken on player.player_id = wicket_taken.player_out where kind_out='bowled' group by player_id order by player_name;

--6--

SELECT match_id, t1.name AS team_1, t2.name AS team_2, t3.name AS winning_team_name, win_margin FROM match INNER JOIN team AS t1 ON match.team_1 = t1.team_id INNER JOIN team AS t2 ON match.team_2 = t2.team_id INNER JOIN team AS t3 ON match_winner = t3.team_id WHERE win_margin>=60 ORDER BY win_margin, match_id;

--7--

select player_name from player where dob > '1988-02-12' and batting_hand = 'Left-hand bat' order by player_name ;

--8--

SELECT batsman_scored.match_id, SUM(batsman_scored.runs_scored)+ COALESCE(SUM(extra_runs.extra_runs),0) AS total_runs FROM batsman_scored LEFT OUTER JOIN extra_runs ON batsman_scored.match_id=extra_runs.match_id AND batsman_scored.ball_id=extra_runs.ball_id AND batsman_scored.innings_no=extra_runs.innings_no AND batsman_scored.over_id=extra_runs.over_id GROUP BY batsman_scored.match_id ORDER BY batsman_scored.match_id;

--9--

select tA.match_id, tA.maximum_runs, player.player_name from (select t3.match_id, t3.over_id, t3.innings_no, SUM(total)as maximum_runs, t3.bowler from (select t1.* , t2.extra_runs, (t1.runs_scored+ coalesce(t2.extra_runs,0)) as total, t2b.bowler from batsman_scored as t1 left join extra_runs as t2 on t1.match_id=t2.match_id and t1.over_id=t2.over_id and t1.ball_id=t2.ball_id and t1.innings_no=t2.innings_no left join ball_by_ball as t2b on t1.match_id=t2b.match_id and t1.over_id=t2b.over_id and t1.ball_id=t2b.ball_id and t1.innings_no=t2b.innings_no) as t3 group by match_id, over_id, innings_no, bowler) as tA left join (select match_id, max(maximum_runs) from (select t3.match_id, t3.over_id, t3.innings_no, SUM(total)as maximum_runs, t3.bowler from (select t1.* , t2.extra_runs, (t1.runs_scored+ coalesce(t2.extra_runs,0)) as total, t2b.bowler from batsman_scored as t1 left join extra_runs as t2 on t1.match_id=t2.match_id and t1.over_id=t2.over_id and t1.ball_id=t2.ball_id and t1.innings_no=t2.innings_no left join ball_by_ball as t2b on t1.match_id=t2b.match_id and t1.over_id=t2b.over_id and t1.ball_id=t2b.ball_id and t1.innings_no=t2b.innings_no) as t3 group by match_id, over_id, innings_no, bowler) as t4 group by match_id) as tB on tA.match_id=tB.match_id left join player on tA.bowler=player.player_id where tA.maximum_runs=tB.max group by tA.match_id, tA.maximum_runs, tA.over_id, player.player_name order by tA.match_id, tA.over_id DESC;

--10--

SELECT player_name, COUNT(player_out) AS number FROM wicket_taken RIGHT OUTER JOIN player ON player_out=player_id AND wicket_taken.kind_out='run out' GROUP BY player_name ORDER BY number DESC, player_name;

--11--

select kind_out as out_type, count(kind_out) from wicket_taken group by kind_out order by count(kind_out) desc, kind_out ;

--12--

SELECT team.name, COUNT(team.name) AS number FROM match INNER JOIN player_match ON player_match.match_id=match.match_id AND match.man_of_the_match=player_match.player_id INNER JOIN team ON player_match.team_id=team.team_id GROUP BY team.name ORDER BY team.name;

--13--

select venue from match inner join (select match_id, count(*) count_wides  from extra_runs where extra_type='wides' group by match_id) as countwides on match.match_id=countwides.match_id group by venue order by sum(countwides.count_wides) desc limit 1;

--14--

SELECT venue FROM match WHERE (toss_winner=team_1 AND toss_decision='field' AND match_winner=team_1) OR (toss_winner=team_1 AND toss_decision='bat' AND match_winner=team_2) OR (toss_winner=team_2 AND toss_decision='field' AND match_winner=team_2) OR (toss_winner=team_2 AND toss_decision='bat' AND match_winner=team_1) GROUP BY venue ORDER BY COUNT(venue) DESC, venue;

--15--

SELECT player_name FROM ball_by_ball INNER JOIN player ON ball_by_ball.bowler=player.player_id INNER JOIN batsman_scored ON batsman_scored.match_id=ball_by_ball.match_id AND batsman_scored.ball_id=ball_by_ball.ball_id AND batsman_scored.innings_no=ball_by_ball.innings_no AND batsman_scored.over_id=ball_by_ball.over_id LEFT OUTER JOIN extra_runs ON ball_by_ball.match_id=extra_runs.match_id AND ball_by_ball.ball_id=extra_runs.ball_id AND ball_by_ball.innings_no=extra_runs.innings_no AND ball_by_ball.over_id=extra_runs.over_id LEFT OUTER JOIN wicket_taken ON  wicket_taken.match_id=ball_by_ball.match_id AND wicket_taken.ball_id=ball_by_ball.ball_id AND wicket_taken.innings_no=ball_by_ball.innings_no AND wicket_taken.over_id=ball_by_ball.over_id AND wicket_taken.player_out=ball_by_ball.striker GROUP BY player_name HAVING COUNT(player_out)>0 ORDER BY ROUND((SUM(runs_scored)+COALESCE(SUM(extra_runs.extra_runs),0))*1.0 / COUNT(player_out),3), player_name LIMIT 1;

--16--

SELECT player_name, name FROM player_match INNER JOIN team ON player_match.team_id=team.team_id INNER JOIN player ON player.player_id=player_match.player_id INNER JOIN match ON player_match.match_id=match.match_id WHERE role='CaptainKeeper' AND match_winner=player_match.team_id ORDER BY player_name, name;

--17--

select player_name, runs_scored from (select striker, count(runs_scored) as runs_scored from batsman_scored as t1 inner join (select match_id, over_id, ball_id, innings_no, striker from ball_by_ball) as t3 on t3.match_id=t1.match_id and t3.over_id=t1.over_id and t3.ball_id=t1.ball_id and t3.innings_no=t1.innings_no group by t3.striker) as t4 inner join (select player_name, player_id from player) as t5 on t5.player_id=t4.striker right join (select distinct striker from (select match_id, striker, sum(runs_scored) as runs_match from (select t6.* , t7.striker from batsman_scored as t6 left join ball_by_ball as t7 on t7.match_id=t6.match_id and t7.over_id=t6.over_id and t7.ball_id=t6.ball_id and t7.innings_no=t6.innings_no) as t9 group by match_id, striker) as t10 where runs_match >= 50) as t11 on t11.striker=player_id where runs_scored >=50 order by runs_scored desc, player_name;

--18--

SELECT player_name FROM batsman_scored INNER JOIN ball_by_ball ON batsman_scored.match_id=ball_by_ball.match_id AND batsman_scored.ball_id=ball_by_ball.ball_id AND batsman_scored.innings_no=ball_by_ball.innings_no AND batsman_scored.over_id=ball_by_ball.over_id INNER JOIN player ON player.player_id=ball_by_ball.striker INNER JOIN match ON match.match_id=batsman_scored.match_id WHERE ball_by_ball.team_bowling=match.match_winner GROUP BY player_name, batsman_scored.innings_no, batsman_scored.match_id HAVING SUM(runs_scored)>=100 ORDER BY player_name;

--19--

select match_id, venue from match as t1 left join (select team_id from team where name= 'Kolkata Knight Riders') as t2 on t1.team_1=t2.team_id or t1.team_2=t2.team_id where t1.match_winner != t2.team_id order by t1.match_id ;

--20--

SELECT player_name FROM ball_by_ball 
INNER JOIN batsman_scored ON batsman_scored.match_id=ball_by_ball.match_id 
AND batsman_scored.ball_id=ball_by_ball.ball_id 
AND batsman_scored.innings_no=ball_by_ball.innings_no 
AND batsman_scored.over_id=ball_by_ball.over_id 
INNER JOIN player ON player.player_id=ball_by_ball.striker OR player.player_id=ball_by_ball.non_striker 
INNER JOIN match ON batsman_scored.match_id=match.match_id 
GROUP BY player_name, match.season_id 
HAVING match.season_id=5
ORDER BY ROUND(SUM(runs_scored)*1.0/COUNT(player_name),3) DESC, player_name LIMIT 10;

--21--

SELECT country_name 
FROM (SELECT player_name,ROUND(SUM(runs_scored)*1.0/COUNT(player_name),3) as r, country_name 
      FROM ball_by_ball INNER JOIN batsman_scored ON batsman_scored.match_id=ball_by_ball.match_id AND batsman_scored.ball_id=ball_by_ball.ball_id AND batsman_scored.innings_no=ball_by_ball.innings_no AND batsman_scored.over_id=ball_by_ball.over_id 
      INNER JOIN match ON batsman_scored.match_id=match.match_id
      RIGHT JOIN player ON player.player_id=ball_by_ball.striker OR player.player_id=ball_by_ball.non_striker 
      GROUP BY player_name, country_name 
      ORDER BY ROUND(SUM(runs_scored)*1.0/COUNT(player_name),3) DESC, player_name, country_name) AS t1 
      GROUP BY country_name ORDER BY AVG(t1.r) DESC, country_name LIMIT 5;